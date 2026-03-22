package com.example.kotlin.security.pipeline.steps

import com.example.kotlin.security.CryptoContext
import com.example.kotlin.security.featureFlag.FeatureFlagService
import com.example.kotlin.security.Signer
import com.example.kotlin.security.services.SignatureService

class SignStep(
    private val signatureService: SignatureService,
    private val signer: Signer
): CryptoStep {
    override fun apply(context: CryptoContext, featureFlagService: FeatureFlagService): CryptoContext =
        if (featureFlagService.isSigningEnabled()) {
            process(context)
        } else {
            context.copy(signature = context.hash)
        }

    override fun process(context: CryptoContext): CryptoContext {
        val hash = requireNotNull(context.hash) { "Hash step must run before sign step" }
        return context.copy(signature = signatureService.sign(hash, signer.type))
    }
}
