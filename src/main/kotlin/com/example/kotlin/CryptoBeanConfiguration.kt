package com.example.kotlin

import com.example.kotlin.security.domain.Algorithm
import com.example.kotlin.security.AesEncrypter
import com.example.kotlin.security.Base64Encoder
import com.example.kotlin.security.pipeline.steps.CanonicalizeStep
import com.example.kotlin.security.factory.CanonicalizerFactory
import com.example.kotlin.security.services.CanonicalizerService
import com.example.kotlin.security.domain.CanonicalizerType
import com.example.kotlin.security.CryptoConfig
import com.example.kotlin.security.services.CryptoService
import com.example.kotlin.security.CustomBaseEncoder
import com.example.kotlin.security.DefaultBillingEventCanonicalizer
import com.example.kotlin.security.services.DefaultCryptoService
import com.example.kotlin.security.DefaultPaymentCanonicalizer
import com.example.kotlin.security.DefaultPrivateKeyProvider
import com.example.kotlin.security.DefaultPublicKeyProvider
import com.example.kotlin.security.DefaultSecretKeyProvider
import com.example.kotlin.security.DefaultStringCanonicalizer
import com.example.kotlin.security.DESedeEncrypter
import com.example.kotlin.security.DesEncrypter
import com.example.kotlin.security.pipeline.DynamicCryptoPipelineExecutor
import com.example.kotlin.security.factory.EncoderFactory
import com.example.kotlin.security.services.EncoderService
import com.example.kotlin.security.pipeline.steps.EncoderStep
import com.example.kotlin.security.domain.EncoderType
import com.example.kotlin.security.Encrypter
import com.example.kotlin.security.domain.EncrypterType
import com.example.kotlin.security.pipeline.steps.EncryptStep
import com.example.kotlin.security.factory.EncryptionFactory
import com.example.kotlin.security.services.EncryptionService
import com.example.kotlin.security.featureFlag.FeatureFlagService
import com.example.kotlin.security.factory.FeatureFlagServiceFactory
import com.example.kotlin.security.HashFactory
import com.example.kotlin.security.services.HashService
import com.example.kotlin.security.pipeline.steps.HashStep
import com.example.kotlin.security.domain.HashType
import com.example.kotlin.security.HexEncoder
import com.example.kotlin.security.hash.MD5HashGenerator
import com.example.kotlin.security.pipeline.PipelineExecutor
import com.example.kotlin.security.PrivateKeyProvider
import com.example.kotlin.security.PublicKeyProvider
import com.example.kotlin.security.RSAEncrypter
import com.example.kotlin.security.RSASigner
import com.example.kotlin.security.hash.Sha256HashGenerator
import com.example.kotlin.security.hash.Sha512HashGenerator
import com.example.kotlin.security.pipeline.steps.SignStep
import com.example.kotlin.security.Signer
import com.example.kotlin.security.services.SignatureService
import com.example.kotlin.security.pipeline.StaticCryptoPipelineExecutor
import com.example.kotlin.security.SecretKeyProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CryptoBeanConfiguration {

    @Bean
    fun canonicalizerFactory(): CanonicalizerFactory =
        CanonicalizerFactory(
            listOf(
                DefaultBillingEventCanonicalizer(),
                DefaultPaymentCanonicalizer(),
                DefaultStringCanonicalizer()
            )
        )

    @Bean
    fun canonicalizerService(canonicalizerFactory: CanonicalizerFactory): CanonicalizerService =
        CanonicalizerService(canonicalizerFactory)

    @Bean
    fun hashFactory(): HashFactory =
        HashFactory(
            listOf(
                Sha256HashGenerator(),
                Sha512HashGenerator(),
                MD5HashGenerator()
            )
        )

    @Bean
    fun hashService(hashFactory: HashFactory): HashService =
        HashService(hashFactory)

    @Bean
    fun encryptionFactory(): EncryptionFactory =
        EncryptionFactory(
            listOf<Encrypter>(
                AesEncrypter(),
                DesEncrypter(),
                RSAEncrypter(),
                DESedeEncrypter()
            )
        )

    @Bean
    fun secretKeyProvider(): SecretKeyProvider = DefaultSecretKeyProvider()

    @Bean
    fun publicKeyProvider(): PublicKeyProvider = DefaultPublicKeyProvider()

    @Bean
    fun privateKeyProvider(): PrivateKeyProvider = DefaultPrivateKeyProvider()

    @Bean
    fun encryptionService(
        encryptionFactory: EncryptionFactory,
        secretKeyProvider: SecretKeyProvider,
        publicKeyProvider: PublicKeyProvider,
        privateKeyProvider: PrivateKeyProvider
    ): EncryptionService =
        EncryptionService(
            encryptionFactory = encryptionFactory,
            secretKeyProvider = secretKeyProvider,
            publicKeyProvider = publicKeyProvider,
            privateKeyProvider = privateKeyProvider
        )

    @Bean
    fun encoderFactory(): EncoderFactory =
        EncoderFactory(
            listOf(
                Base64Encoder(),
                HexEncoder(),
                CustomBaseEncoder("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz+/")
            )
        )

    @Bean
    fun encoderService(encoderFactory: EncoderFactory): EncoderService =
        EncoderService(encoderFactory)

    @Bean
    fun signer(privateKeyProvider: PrivateKeyProvider): Signer =
        RSASigner(privateKeyProvider)

    @Bean
    fun signatureService(signer: Signer): SignatureService =
        SignatureService(signer)

    @Bean
    fun featureFlagService(): FeatureFlagService =
        FeatureFlagServiceFactory.create()

    @Bean
    fun cryptoConfig(signer: Signer): CryptoConfig =
        CryptoConfig(
            defaultHash = HashType.SHA_256,
            defaultEncrypter = EncrypterType.AES,
            defaultEncoder = EncoderType.BASE64,
            defaultSigner = signer
        )

    @Bean
    fun cryptoService(
        hashService: HashService,
        signatureService: SignatureService,
        encryptionService: EncryptionService,
        canonicalizerService: CanonicalizerService,
        encoderService: EncoderService
    ): CryptoService =
        DefaultCryptoService(
            hashService = hashService,
            signatureService = signatureService,
            encryptionService = encryptionService,
            canonicalizerService = canonicalizerService,
            encoderService = encoderService
        )

    @Bean("staticCryptoPipelineExecutor")
    fun staticPipelineExecutor(
        cryptoConfig: CryptoConfig,
        featureFlagService: FeatureFlagService,
        canonicalizerService: CanonicalizerService,
        hashService: HashService,
        signatureService: SignatureService,
        encryptionService: EncryptionService,
        encoderService: EncoderService
    ): PipelineExecutor =
        StaticCryptoPipelineExecutor(
            config = cryptoConfig,
            featureFlagService = featureFlagService,
            canonicalizerService = canonicalizerService,
            hashService = hashService,
            signatureService = signatureService,
            encryptionService = encryptionService,
            encoderService = encoderService
        )

    @Bean("dynamicCryptoPipelineExecutor")
    fun dynamicPipelineExecutor(
        canonicalizerService: CanonicalizerService,
        hashService: HashService,
        signatureService: SignatureService,
        encryptionService: EncryptionService,
        encoderService: EncoderService,
        signer: Signer,
        featureFlagService: FeatureFlagService
    ): PipelineExecutor =
        DynamicCryptoPipelineExecutor(
            steps = listOf(
                CanonicalizeStep(canonicalizerService, CanonicalizerType.STRING),
                HashStep(hashService, HashType.SHA_256),
                SignStep(signatureService, signer),
                EncryptStep(encryptionService, Algorithm.AES),
                EncoderStep(encoderService, EncoderType.BASE64)
            ),
            featureFlagService = featureFlagService
        )
}
