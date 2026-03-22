package com.example.kotlin.security.pipeline.steps

import com.example.kotlin.security.CryptoContext
import com.example.kotlin.security.domain.HashType
import com.example.kotlin.security.services.HashService

class HashStep(
    private val hashService: HashService,
    private val hashType: HashType
): CryptoStep {
    override fun process(context: CryptoContext): CryptoContext {
        val dataToHash = context.canonicalData ?: context.rawData
        return context.copy(hash = hashService.hash(dataToHash, hashType))
    }
}
