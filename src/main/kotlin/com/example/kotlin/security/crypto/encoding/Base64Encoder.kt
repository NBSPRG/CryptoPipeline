package com.example.kotlin.security

import java.util.Base64

class Base64Encoder: Encoder {
    override val type = EncoderType.BASE64
    
    override fun encode(bytes: ByteArray): String {
        return Base64.getEncoder().encodeToString(bytes)
    }

    override fun decode(string: String): ByteArray {
        return Base64.getDecoder().decode(string)
    }
}
