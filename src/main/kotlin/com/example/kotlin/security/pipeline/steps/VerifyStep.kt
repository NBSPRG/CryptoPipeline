package com.example.kotlin.security

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
