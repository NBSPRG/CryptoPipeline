package com.example.kotlin.security

class EncoderStep(
    private val encoderService: EncoderService,
    private val encoderType: EncoderType
): CryptoStep {
    override fun process(context: CryptoContext): CryptoContext {
        val payload = context.encrypted ?: context.signature ?: context.hash
            ?: error("Encoder step requires hash, signature, or encrypted payload")
        return context.copy(encoded = encoderService.encode(payload, encoderType))
    }
}
