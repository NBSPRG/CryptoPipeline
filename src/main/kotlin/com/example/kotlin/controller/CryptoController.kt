package com.example.kotlin.controller

import com.example.kotlin.security.CryptoRequest
import com.example.kotlin.security.CryptoPipelineResponse
import com.example.kotlin.security.domain.BulkyResult
import com.example.kotlin.security.domain.CanonicalizerType
import com.example.kotlin.security.domain.EncoderType
import com.example.kotlin.security.domain.EncrypterType
import com.example.kotlin.security.domain.HashType
import com.example.kotlin.security.pipeline.BulkyPipelineExecutor
import com.example.kotlin.security.pipeline.PipelineExecutor
import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/pipeline")
class CryptoController(
    @param:Qualifier("dynamicCryptoPipelineExecutor") private val executor: PipelineExecutor,
    private val bulkyExecutor: BulkyPipelineExecutor
) {

    @PostMapping("/execute")
    suspend fun execute(
        @Valid @RequestBody request: PipelineRequest
    ): PipelineResponse {
        val cryptoRequest = request.toCryptoRequest()
        val result = executor.execute(cryptoRequest)
        return PipelineResponse.from(result)
    }

    @PostMapping("/execute/bulk")
    suspend fun executeBulk(
        @Valid @RequestBody request: BulkPipelineRequest
    ): BulkPipelineResponse {
        val cryptoRequests = request.requests.map { it.toCryptoRequest() }
        val result = bulkyExecutor.executeBulk(cryptoRequests, request.concurrency)
        return BulkPipelineResponse.from(result)
    }
}


data class PipelineRequest(
    @field:NotBlank(message = "data must not be blank")
    val data: String,
    val canonicalizerType: CanonicalizerType = CanonicalizerType.STRING,
    val hashType: HashType = HashType.SHA_256,
    val encrypterType: EncrypterType = EncrypterType.RSA,
    val encoderType: EncoderType = EncoderType.BASE64,
    val keyId: String? = null,
    val version: Int = 1,
    val metadata: Map<String, Any> = emptyMap()
) {
    fun toCryptoRequest(): CryptoRequest<String> = CryptoRequest(
        data = data,
        canonicalizerType = canonicalizerType,
        hashType = hashType,
        encrypterType = encrypterType,
        encoderType = encoderType,
        keyId = keyId,
        version = version,
        metadata = metadata
    )
}

data class BulkPipelineRequest(
    @field:NotEmpty(message = "requests must not be empty")
    val requests: List<PipelineRequest>,
    @field:Min(1) @field:Max(50)
    val concurrency: Int = 10
)


data class PipelineResponse(
    val hash: StageResponse,
    val sign: StageResponse,
    val encrypt: StageResponse
) {
    companion object {
        fun from(result: CryptoPipelineResponse): PipelineResponse =
            PipelineResponse(
                hash = StageResponse(
                    encoded = result.hash.encoded,
                    algorithm = result.hash.hashAlgorithm.name,
                    version = result.hash.version
                ),
                sign = StageResponse(
                    encoded = result.sign.encoded,
                    algorithm = result.sign.signatureAlgorithm.name,
                    version = result.sign.version
                ),
                encrypt = StageResponse(
                    encoded = result.encrypt.encoded,
                    algorithm = result.encrypt.encryptionAlgorithm.name,
                    version = result.encrypt.version
                )
            )
    }
}

data class StageResponse(
    val encoded: String?,
    val algorithm: String,
    val version: Int
)

data class BulkPipelineResponse(
    val totalProcessed: Int,
    val successCount: Int,
    val failureCount: Int,
    val totalTimeMs: Long,
    val successful: List<IndexedPipelineResponse>,
    val failed: List<IndexedFailureResponse>
) {
    companion object {
        fun from(result: BulkyResult): BulkPipelineResponse =
            BulkPipelineResponse(
                totalProcessed = result.totalProcessed,
                successCount = result.successfulCount,
                failureCount = result.failedCount,
                totalTimeMs = result.totalTimeMs,
                successful = result.successful.map {
                    IndexedPipelineResponse(
                        index = it.index,
                        response = PipelineResponse.from(it.value)
                    )
                },
                failed = result.failed.map {
                    IndexedFailureResponse(
                        index = it.index,
                        error = it.value.message ?: "Unknown error"
                    )
                }
            )
    }
}

data class IndexedPipelineResponse(
    val index: Int,
    val response: PipelineResponse
)

data class IndexedFailureResponse(
    val index: Int,
    val error: String
)
