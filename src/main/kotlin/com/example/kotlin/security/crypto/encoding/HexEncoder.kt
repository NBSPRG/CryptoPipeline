package com.example.kotlin.security

import com.example.kotlin.security.domain.EncoderType

class HexEncoder: Encoder {
    override val type = EncoderType.HEX

    override fun encode(bytes: ByteArray): String {
        val sb = StringBuilder()
        for (b in bytes) {
            val hex = Integer.toHexString(0xff and b.toInt())
            if (hex.length == 1) sb.append('0')
            sb.append(hex)
        }
        return sb.toString()
    }

    override fun decode(string: String): ByteArray {
        require(string.length % 2 == 0) 
            { "Hex input must contain even number of characters "}
        return string.chunked(2)
            .map{ it.toInt(16).toByte() }
            .toByteArray()
    }
}
