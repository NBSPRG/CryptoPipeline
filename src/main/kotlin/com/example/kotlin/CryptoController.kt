package com.example.kotlin

import com.example.kotlin.security.Algorithm
import com.example.kotlin.security.CanonicalizerType
import com.example.kotlin.security.CompanyProduct
import com.example.kotlin.security.CryptoRequest
import com.example.kotlin.security.CryptoService
import com.example.kotlin.security.Duration
import com.example.kotlin.security.EncoderType
import com.example.kotlin.security.EncrypterType
import com.example.kotlin.security.FeatureFlagService
import com.example.kotlin.security.HashType
import com.example.kotlin.security.LineCode
import com.example.kotlin.security.PipelineExecutor
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.util.Base64

@RestController
@RequestMapping("/api")
class CryptoController(
    private val billingEventGenerationService: BillingEventGenerationService,
    private val cryptoService: CryptoService,
    private val featureFlagService: FeatureFlagService,
    @Qualifier("staticPipelineExecutor")
    private val staticPipelineExecutor: PipelineExecutor,
    @Qualifier("dynamicPipelineExecutor")
    private val dynamicPipelineExecutor: PipelineExecutor
) {

    @GetMapping("/health")
    fun health(): Map<String, String> =
        mapOf("status" to "ok")

    @GetMapping("/feature-flags")
    fun featureFlags(): FeatureFlagsResponse =
        FeatureFlagsResponse(
            source = featureFlagService.source,
            canonicalizationEnabled = featureFlagService.isCanonicalizationEnabled(),
            signingEnabled = featureFlagService.isSigningEnabled(),
            encryptionEnabled = featureFlagService.isEncryptionEnabled()
        )

    @PostMapping("/billing-events/monthly")
    fun generateMonthlyBillingEvents(
        @RequestBody request: BillingEventGenerationRequest
    ): BillingEventGenerationResponse {
        val events = billingEventGenerationService.generateMonthlyBillingEvents(
            duration = Duration(request.startDate, request.endDate),
            companyProduct = CompanyProduct(
                orderId = request.companyProduct.id,
                companyId = request.companyProduct.companyNumber,
                lineCode = request.companyProduct.lineCode,
                dimension = request.companyProduct.dimensions
            )
        )

        return BillingEventGenerationResponse(
            count = events.size,
            events = events
        )
    }

    @PostMapping("/crypto/hash")
    fun hash(@RequestBody request: TextCryptoRequest): CryptoOperationResponse {
        val response = cryptoService.hash(request.toCryptoRequest())
        return CryptoOperationResponse(
            rawBase64 = response.raw.toBase64(),
            encoded = response.encoded,
            algorithm = response.hashAlgorithm.name,
            version = response.version,
            metadata = response.metadata
        )
    }

    @PostMapping("/crypto/sign")
    fun sign(@RequestBody request: TextCryptoRequest): CryptoOperationResponse {
        val response = cryptoService.sign(request.toCryptoRequest())
        return CryptoOperationResponse(
            rawBase64 = response.raw.toBase64(),
            encoded = response.encoded,
            algorithm = response.signatureAlgorithm.name,
            version = response.version,
            metadata = response.metadata
        )
    }

    @PostMapping("/crypto/encrypt")
    fun encrypt(@RequestBody request: TextCryptoRequest): CryptoOperationResponse {
        val response = cryptoService.encrypt(request.toCryptoRequest())
        return CryptoOperationResponse(
            rawBase64 = response.raw.toBase64(),
            encoded = response.encoded,
            algorithm = response.encryptionAlgorithm.name,
            version = response.version,
            metadata = response.metadata
        )
    }

    @PostMapping("/crypto/pipeline/static")
    fun executeStaticPipeline(@RequestBody request: TextCryptoRequest): PipelineOperationResponse =
        staticPipelineExecutor.execute(request.toCryptoRequest()).toResponse()

    @PostMapping("/crypto/pipeline/dynamic")
    fun executeDynamicPipeline(@RequestBody request: TextCryptoRequest): PipelineOperationResponse =
        dynamicPipelineExecutor.execute(request.toCryptoRequest()).toResponse()

    private fun TextCryptoRequest.toCryptoRequest(): CryptoRequest<String> =
        CryptoRequest(
            data = data,
            canonicalizerType = canonicalizerType,
            hashType = hashType,
            encrypterType = encrypterType,
            encoderType = encoderType,
            keyId = keyId,
            version = version,
            metadata = metadata
        )

    private fun ByteArray.toBase64(): String =
        Base64.getEncoder().encodeToString(this)

    private fun com.example.kotlin.security.CryptoPipelineResponse.toResponse(): PipelineOperationResponse =
        PipelineOperationResponse(
            hash = CryptoOperationResponse(
                rawBase64 = hash.raw.toBase64(),
                encoded = hash.encoded,
                algorithm = hash.hashAlgorithm.name,
                version = hash.version,
                metadata = hash.metadata
            ),
            sign = CryptoOperationResponse(
                rawBase64 = sign.raw.toBase64(),
                encoded = sign.encoded,
                algorithm = sign.signatureAlgorithm.name,
                version = sign.version,
                metadata = sign.metadata
            ),
            encrypt = CryptoOperationResponse(
                rawBase64 = encrypt.raw.toBase64(),
                encoded = encrypt.encoded,
                algorithm = encrypt.encryptionAlgorithm.name,
                version = encrypt.version,
                metadata = encrypt.metadata
            )
        )
}

data class BillingEventGenerationRequest(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val companyProduct: CompanyProductRequest
)

data class CompanyProductRequest(
    val id: Int,
    val companyNumber: String,
    val lineCode: LineCode,
    val dimensions: Map<String, String> = emptyMap()
)

data class BillingEventGenerationResponse(
    val count: Int,
    val events: List<GeneratedBillingEvent>
)

data class TextCryptoRequest(
    val data: String,
    val canonicalizerType: CanonicalizerType = CanonicalizerType.STRING,
    val hashType: HashType = HashType.SHA_256,
    val encrypterType: EncrypterType = EncrypterType.RSA,
    val encoderType: EncoderType = EncoderType.BASE64,
    val keyId: String? = null,
    val version: Int = 1,
    val metadata: Map<String, Any> = emptyMap()
)

data class FeatureFlagsResponse(
    val source: String,
    val canonicalizationEnabled: Boolean,
    val signingEnabled: Boolean,
    val encryptionEnabled: Boolean
)

data class CryptoOperationResponse(
    val rawBase64: String,
    val encoded: String?,
    val algorithm: String,
    val version: Int,
    val metadata: Map<String, Any>
)

data class PipelineOperationResponse(
    val hash: CryptoOperationResponse,
    val sign: CryptoOperationResponse,
    val encrypt: CryptoOperationResponse
)
