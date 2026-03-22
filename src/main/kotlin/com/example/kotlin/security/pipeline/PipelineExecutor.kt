package com.example.kotlin.security.pipeline

import com.example.kotlin.security.CryptoPipelineResponse
import com.example.kotlin.security.CryptoRequest

interface PipelineExecutor {
    val type: String
    fun <T> execute(request: CryptoRequest<T>): CryptoPipelineResponse
}
