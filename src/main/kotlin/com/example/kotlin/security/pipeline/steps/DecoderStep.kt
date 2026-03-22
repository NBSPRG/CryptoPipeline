package com.example.kotlin.security.pipeline.steps

import com.example.kotlin.security.CryptoContext
import com.example.kotlin.security.domain.EncoderType
import com.example.kotlin.security.services.EncoderService

class DecoderStep(
    private val encoderService: EncoderService,
    private val encoderType: EncoderType,
    private val targetField: DecoderTarget
): CryptoStep {
    override fun process(context: CryptoContext): CryptoContext {
        val encoded = context.encoded?: 
            error("Decoder step requrires encoded payload")

        val decoded = encoderService.decode(encoded, encoderType)
        return when(targetField) {
            DecoderTarget.ENCRYPTED -> context.copy(encrypted = decoded)
            DecoderTarget.SIGNATURE -> context.copy(signature = decoded)
            DecoderTarget.HASH      -> context.copy(hash = decoded)
        }
    }
}

enum class DecoderTarget {
    ENCRYPTED,
    SIGNATURE,
    HASH
}