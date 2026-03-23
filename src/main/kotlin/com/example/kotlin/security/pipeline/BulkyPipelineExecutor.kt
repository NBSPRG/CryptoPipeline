package com.example.kotlin.security.pipeline

import com.example.kotlin.security.CryptoRequest
import com.example.kotlin.security.domain.BulkyResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class BulkyPipelineExecutor(
    @param:Qualifier("dynamicCryptoPipelineExecutor") private val delegate: PipelineExecutor,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend fun <T> executeBulk(
        requests: List<CryptoRequest<T>>,
        concurrency: Int = 10
    ): BulkyResult = coroutineScope {
        val semaphore = Semaphore(concurrency)
        val startTime = System.currentTimeMillis()

        requests
            .mapIndexed { index, request ->
                async(dispatcher) {
                    semaphore.withPermit {
                        runCatching {
                            IndexedValue(index, delegate.execute(request))
                        }
                    }
                }
            }
            .awaitAll()
            .let { BulkyResult.from(it, startTime) }
    }
}