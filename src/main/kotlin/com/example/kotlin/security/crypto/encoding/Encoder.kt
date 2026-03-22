package com.example.kotlin.security

interface Encoder {
    val type: EncoderType
    fun encode(bytes: ByteArray): String
    fun decode(string: String): ByteArray
}