package com.example.kotlin.security

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
