package com.example.kotlin.security.pipeline

import com.example.kotlin.security.CryptoContext
import com.example.kotlin.security.CryptoPipelineResponse
import com.example.kotlin.security.CryptoRequest
import com.example.kotlin.security.pipeline.steps.CryptoStep
import com.example.kotlin.security.featureFlag.FeatureFlagService

class DynamicCryptoPipelineExecutor(
    private val steps: List<CryptoStep>,
    private val featureFlagService: FeatureFlagService
): PipelineExecutor {

    override val type = "DYNAMIC"
    
    override fun <T> execute(request: CryptoRequest<T>): CryptoPipelineResponse {
        var cryptoContext = CryptoContext.Companion.from(request)

        for (step in steps) {
            cryptoContext = step.apply(cryptoContext, featureFlagService)
        }

        return CryptoPipelineResponse.Companion.from(cryptoContext, request)
    }
}
