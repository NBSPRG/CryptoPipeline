package com.example.kotlin.security.domain

import com.example.kotlin.security.CryptoPipelineResponse

data class BulkyResult(
    val successful: List<IndexedValue<CryptoPipelineResponse>>,
    val failed: List<IndexedValue<Throwable>>,
    val totalProcessed: Int,
    val successfulCount: Int,
    val failedCount: Int,
    val totalTimeMs: Long,
) {
    companion object {
        fun from(
            result: List<Result<IndexedValue<CryptoPipelineResponse>>>,
            startTime: Long,
        ): BulkyResult {
            val successful = result
                .filter { it.isSuccess }
                .map{ it.getOrThrow() }

            val failed = result
                .filter { it.isFailure }
                .mapIndexed { index, result ->
                    IndexedValue(index, result.exceptionOrNull()!!)
                }

            return BulkyResult(
                successful = successful,
                failed = failed,
                totalProcessed = result.size,
                successfulCount = successful.size,
                failedCount = failed.size,
                totalTimeMs = System.currentTimeMillis() - startTime
            )
        }
    }
}