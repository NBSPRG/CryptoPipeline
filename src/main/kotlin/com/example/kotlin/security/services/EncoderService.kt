package com.example.kotlin.security.services

import com.example.kotlin.security.domain.EncoderType
import com.example.kotlin.security.factory.EncoderFactory

class EncoderService(
    private val factory: EncoderFactory
) {
    fun encode(data: ByteArray, type: EncoderType): String {
        val encoder = factory[type]
        return encoder.encode(data)
    }

    fun decode(data: String, type: EncoderType): ByteArray {
        val encoder = factory[type]
        return encoder.decode(data)
    }
}
