package com.example.kotlin.security.pipeline.steps

import com.example.kotlin.security.CryptoContext
import com.example.kotlin.security.featureFlag.FeatureFlagService

interface CryptoStep {
    fun apply(context: CryptoContext, featureFlagService: FeatureFlagService): CryptoContext =
        process(context)

    fun process(context: CryptoContext): CryptoContext
}
