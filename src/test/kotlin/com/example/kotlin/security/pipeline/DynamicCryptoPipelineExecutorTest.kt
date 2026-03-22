package com.example.kotlin.security.pipeline

import com.example.kotlin.security.AesEncrypter
import com.example.kotlin.security.CryptoRequest
import com.example.kotlin.security.DefaultSecretKeyProvider
import com.example.kotlin.security.DefaultStringCanonicalizer
import com.example.kotlin.security.HashFactory
import com.example.kotlin.security.Signer
import com.example.kotlin.security.domain.Algorithm
import com.example.kotlin.security.domain.CanonicalizerType
import com.example.kotlin.security.domain.EncoderType
import com.example.kotlin.security.domain.EncrypterType
import com.example.kotlin.security.domain.HashType
import com.example.kotlin.security.factory.CanonicalizerFactory
import com.example.kotlin.security.factory.EncryptionFactory
import com.example.kotlin.security.featureFlag.FeatureFlagService
import com.example.kotlin.security.hash.Sha256HashGenerator
import com.example.kotlin.security.pipeline.steps.CanonicalizeStep
import com.example.kotlin.security.pipeline.steps.EncryptStep
import com.example.kotlin.security.pipeline.steps.HashStep
import com.example.kotlin.security.pipeline.steps.SignStep
import com.example.kotlin.security.services.CanonicalizerService
import com.example.kotlin.security.services.EncryptionService
import com.example.kotlin.security.services.HashService
import com.example.kotlin.security.services.SignatureService
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DynamicCryptoPipelineExecutorTest {
    private val canonicalizerService = CanonicalizerService(
        CanonicalizerFactory(listOf(DefaultStringCanonicalizer()))
    )
    private val hashService = HashService(
        HashFactory(listOf(Sha256HashGenerator()))
    )
    private val testSigner = TestSigner()
    private val signatureService = SignatureService(testSigner)
    private val encryptionService = EncryptionService(
        encryptionFactory = EncryptionFactory(listOf(AesEncrypter())),
        secretKeyProvider = DefaultSecretKeyProvider()
    )

    @Test
    fun `uses fallback payloads when signing and encryption flags are disabled`() {
        val executor = DynamicCryptoPipelineExecutor(
            steps = listOf(
                CanonicalizeStep(canonicalizerService, CanonicalizerType.STRING),
                HashStep(hashService, HashType.SHA_256),
                SignStep(signatureService, testSigner),
                EncryptStep(encryptionService, Algorithm.AES)
            ),
            featureFlagService = TestFeatureFlagService(
                canonicalizationEnabled = false,
                signingEnabled = false,
                encryptionEnabled = false
            )
        )

        val request = CryptoRequest(
            data = "  hello world  ",
            canonicalizerType = CanonicalizerType.STRING,
            hashType = HashType.SHA_256,
            encrypterType = EncrypterType.AES,
            encoderType = EncoderType.BASE64,
            version = 1
        )

        val response = executor.execute(request)
        val rawHash = hashService.hash(request.data.toString(), HashType.SHA_256)

        assertContentEquals(rawHash, response.hash.raw)
        assertContentEquals(response.hash.raw, response.sign.raw)
        assertContentEquals(response.sign.raw, response.encrypt.raw)
    }

    @Test
    fun `runs canonicalize sign and encrypt steps when flags are enabled`() {
        val executor = DynamicCryptoPipelineExecutor(
            steps = listOf(
                CanonicalizeStep(canonicalizerService, CanonicalizerType.STRING),
                HashStep(hashService, HashType.SHA_256),
                SignStep(signatureService, testSigner),
                EncryptStep(encryptionService, Algorithm.AES)
            ),
            featureFlagService = TestFeatureFlagService(
                canonicalizationEnabled = true,
                signingEnabled = true,
                encryptionEnabled = true
            )
        )

        val request = CryptoRequest(
            data = "  hello world  ",
            canonicalizerType = CanonicalizerType.STRING,
            hashType = HashType.SHA_256,
            encrypterType = EncrypterType.AES,
            encoderType = EncoderType.BASE64,
            version = 1
        )

        val response = executor.execute(request)
        val rawHash = hashService.hash(request.data.toString(), HashType.SHA_256)
        val canonicalHash = hashService.hash("input:HELLO WORLD", HashType.SHA_256)

        assertFalse(response.hash.raw.contentEquals(rawHash))
        assertContentEquals(canonicalHash, response.hash.raw)
        assertFalse(response.sign.raw.contentEquals(response.hash.raw))
        assertFalse(response.encrypt.raw.contentEquals(response.sign.raw))
        assertTrue(response.sign.raw.decodeToString().endsWith("-signed"))
    }
}

private class TestFeatureFlagService(
    private val canonicalizationEnabled: Boolean,
    private val signingEnabled: Boolean,
    private val encryptionEnabled: Boolean
) : FeatureFlagService {
    override val source: String = "Test"

    override fun isCanonicalizationEnabled(): Boolean = canonicalizationEnabled

    override fun isSigningEnabled(): Boolean = signingEnabled

    override fun isEncryptionEnabled(): Boolean = encryptionEnabled
}

private class TestSigner : Signer {
    override val type: Algorithm = Algorithm.RSA

    override fun sign(payload: ByteArray): ByteArray =
        payload + "-signed".encodeToByteArray()
}
