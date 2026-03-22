package com.example.kotlin.security

import com.example.kotlin.security.domain.EncoderType
import java.math.BigInteger

class CustomBaseEncoder(
    private val alphabet: String
): Encoder {
    override val type = EncoderType.CUSTOM

    init {
        require(alphabet.length > 1) { "Custom alphabet must contain at least two characters" }
        require(alphabet.toSet().size == alphabet.length) { "Custom alphabet must not contain duplicate characters" }
    }

    override fun encode(bytes: ByteArray): String {
        var number = BigInteger(1, bytes)
        val base = alphabet.length
        val sb = StringBuilder()

        if (number == BigInteger.ZERO) return alphabet[0].toString()

        while (number > BigInteger.ZERO) {
            val divRem = number.divideAndRemainder(BigInteger.valueOf(base.toLong()))
            sb.append(alphabet[divRem[1].toInt()])
            number = divRem[0]
        }

        return sb.reverse().toString()
    }

    override fun decode(string: String): ByteArray {
        val base = BigInteger.valueOf(alphabet.length.toLong())
        var number = BigInteger.ZERO

        for (char in string) {
            val index = alphabet.indexOf(char)
            require(index >= 0)
                { "Character $char is not part of custom alphabet "}
            
            number = number.multiply(base).add(BigInteger.valueOf(index.toLong()))
        }

        val bytes = number.toByteArray()
        return if(
            bytes.size > 1 &&
            bytes.first() == 0.toByte()
        ) {
            bytes.copyOfRange(1, bytes.size)
        } else {
            bytes
        }
    }
}
