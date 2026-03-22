package com.example.kotlin.security.pipeline.steps

import com.example.kotlin.security.CryptoContext
import com.example.kotlin.security.featureFlag.FeatureFlagService
import com.example.kotlin.security.domain.CanonicalizerType
import com.example.kotlin.security.services.CanonicalizerService

class CanonicalizeStep(
    private val canonicalizerService: CanonicalizerService,
    private val canonicalizerType: CanonicalizerType
): CryptoStep {
    override fun apply(context: CryptoContext, featureFlagService: FeatureFlagService): CryptoContext =
        if (featureFlagService.isCanonicalizationEnabled()) {
            process(context)
        } else {
            context
        }

    override fun process(context: CryptoContext): CryptoContext {
        return context.copy(
            canonicalData = canonicalizerService.canonicalize(context.inputData, canonicalizerType)
        )
    }
}
