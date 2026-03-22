package com.example.kotlin.security.pipeline.steps

import com.example.kotlin.security.CryptoContext
import com.example.kotlin.security.Verifier

class VerifyStep(
    private val verifier: Verifier
) : CryptoStep {
    override fun process(context: CryptoContext): CryptoContext {
        val payload = requireNotNull(context.hash) {
            "Hash step must run before verify step"
        }
        val signature = requireNotNull(context.signature) {
            "Sign or decrypt step must run before verify step"
        }

        require(verifier.verify(payload, signature)) {
            "Signature verification failed"
        }

        return context
    }
}
