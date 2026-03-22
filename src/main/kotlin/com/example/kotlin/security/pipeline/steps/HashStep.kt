package com.example.kotlin.security

class HashStep(
    private val hashService: HashService,
    private val hashType: HashType
): CryptoStep {
    override fun process(context: CryptoContext): CryptoContext {
        val dataToHash = context.canonicalData ?: context.rawData
        return context.copy(hash = hashService.hash(dataToHash, hashType))
    }
}
