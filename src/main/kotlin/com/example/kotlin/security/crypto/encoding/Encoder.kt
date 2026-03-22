package com.example.kotlin.security

import com.example.kotlin.security.domain.EncoderType

interface Encoder {
    val type: EncoderType
    fun encode(bytes: ByteArray): String
    fun decode(string: String): ByteArray
}