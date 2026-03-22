package com.example.kotlin.security

interface CryptoStep {
    fun apply(context: CryptoContext, featureFlagService: FeatureFlagService): CryptoContext =
        process(context)

    fun process(context: CryptoContext): CryptoContext
}
