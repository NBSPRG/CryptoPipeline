package com.example.kotlin.security

import com.example.kotlin.security.domain.Algorithm
import com.example.kotlin.security.domain.toEncrypterType
import com.example.kotlin.security.pipeline.steps.CryptoStep
import com.example.kotlin.security.services.EncryptionService

class DecryptStep(
    private val encryptionService: EncryptionService,
    private val algorithm: Algorithm
) : CryptoStep {
    override fun process(context: CryptoContext): CryptoContext {
        val payload = requireNotNull(context.encrypted) {
            "Encrypt step must run before decrypt step"
        }

        val decrypted = encryptionService.decrypt(payload, algorithm.toEncrypterType())
        return context.copy(signature = decrypted)
    }
}
