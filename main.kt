import java.time.LocalDate
import java.security.KeyPairGenerator
import java.security.PublicKey
import java.security.PrivateKey
import java.util.UUID
import kotlin.random.Random

fun generateMonthlyBillingEvent(
    duration: Duration,
    companyProduct: CompanyProduct
): List<Pair<BillingEvent, String>> {
    val result = mutableListOf<Pair<BillingEvent, String>>()
    val shaGenerator = Sha256HashGenerator()
    val canonicalizer = DefaultBillingEventCanonicalizer()
    val encoder = Base64Encoder()

    var currentMonthStart = duration.startDate.withDayOfMonth(1)

    while (!currentMonthStart.isAfter(duration.endDate)) {
        val currentMonthEnd = currentMonthStart.withDayOfMonth(
            currentMonthStart.month.length(currentMonthStart.isLeapYear)
        )

        val billingEvent = BillingEvent(
            id = Random.nextInt(1, 10000),
            transactionId = UUID.randomUUID().toString(),
            companyProduct = companyProduct,
            duration = Duration(
                startDate = currentMonthStart,
                endDate = currentMonthEnd
            ),
            hashV2 = null
        )

        val stringToHash = canonicalizer.canonicalize(billingEvent)
        val hash = encoder.encode(shaGenerator.hash(stringToHash))

        result.add(Pair(billingEvent, hash))
        currentMonthStart = currentMonthStart.plusMonths(1)
    }

    return result
}

fun main() {
    val duration = Duration(
        LocalDate.of(2026, 3, 1),
        LocalDate.of(2027, 2, 28)
    )
    val companyProduct = CompanyProduct(
        1,
        "123456",
        LineCode.LATE_PAYMENT_FEE,
        mapOf(
            "OFFERING" to "EOR",
            "COUNTRY" to "IND"
        )
    )

    val events = generateMonthlyBillingEvent(duration, companyProduct)
    events.forEachIndexed { index, (event, hash) ->
        println("Event #${index + 1}")
        println("Month : ${event.duration.startDate} -> ${event.duration.endDate}")
        println("Hash  : $hash")
        println("------")
    }

    println("Generated ${events.size} monthly billing events.")

    println("\n=== SERVICE INTEGRATION TESTS ===")

    val canonicalizerFactory = CanonicalizerFactory(
        listOf(
            DefaultBillingEventCanonicalizer(),
            DefaultPaymentCanonicalizer(),
            DefaultStringCanonicalizer()
        )
    )
    val canonicalizerService = CanonicalizerService(canonicalizerFactory)

    val hashFactory = HashFactory(
        listOf(
            Sha256HashGenerator(),
            Sha512HashGenerator(),
            MD5HashGenerator()
        )
    )
    val hashService = HashService(hashFactory)

    val encryptionFactory = EncryptionFactory(
        listOf(
            AesEncrypter(),
            DesEncrypter(),
            RSAEncrypter(),
            DESedeEncrypter()
        )
    )
    val secretKeyProvider = DefaultSecretKeyProvider()

    val encoderFactory = EncoderFactory(
        listOf(
            Base64Encoder(),
            HexEncoder(),
            CustomBaseEncoder("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz+/")
        )
    )
    val encoderService = EncoderService(encoderFactory)

    val rsaKeyPair = KeyPairGenerator.getInstance("RSA").run {
        initialize(2048)
        generateKeyPair()
    }

    val privateKeyProvider = object : PrivateKeyProvider {
        override fun getOrCreate(algorithm: Algorithm): PrivateKey {
            require(algorithm == Algorithm.RSA) { "Only RSA is supported by this test provider" }
            return rsaKeyPair.private
        }
    }

    val publicKeyProvider = object : PublicKeyProvider {
        override fun getOrCreate(algorithm: Algorithm): PublicKey {
            require(algorithm == Algorithm.RSA) { "Only RSA is supported by this test provider" }
            return rsaKeyPair.public
        }
    }

    val encryptionService = EncryptionService(
        encryptionFactory = encryptionFactory,
        secretKeyProvider = secretKeyProvider,
        publicKeyProvider = publicKeyProvider,
        privateKeyProvider = privateKeyProvider
    )

    val signer = RSASigner(privateKeyProvider)
    val signatureService = SignatureService(signer)

    val cryptoService = DefaultCryptoService(
        hashService = hashService,
        signatureService = signatureService,
        encryptionService = encryptionService,
        canonicalizerService = canonicalizerService,
        encoderService = encoderService
    )

    val pipelineConfig = CryptoConfig(
        defaultHash = HashType.SHA_256,
        defaultEncrypter = EncrypterType.AES,
        defaultEncoder = EncoderType.BASE64,
        defaultSigner = signer
    )
    val featureFlagService = StaticFeatureFlagService(
        canonicalizationEnabled = true,
        signingEnabled = true,
        encryptionEnabled = true
    )
    val cryptoPipelineExecutor = StaticCryptoPipelineExecutor(
        config = pipelineConfig,
        featureFlagService = featureFlagService,
        canonicalizerService = canonicalizerService,
        hashService = hashService,
        signatureService = signatureService,
        encryptionService = encryptionService,
        encoderService = encoderService
    )

    val sampleEvent = events.first().first
    println("sample event: $sampleEvent")

    println("\n[1] Canonicalization Service")
    val canonicalBilling = canonicalizerService.canonicalize(sampleEvent, CanonicalizerType.BILLING_EVENT)
    val canonicalString = canonicalizerService.canonicalize("  hello world  ", CanonicalizerType.STRING)
    println("Billing canonical: $canonicalBilling")
    println("String canonical : $canonicalString")

    println("\n[2] Hash Service")
    val hashBytes = hashService.hash(canonicalBilling, HashType.SHA_256)
    val hashEncoded = encoderService.encode(hashBytes, EncoderType.BASE64)
    println("SHA-256 (base64): $hashEncoded")

    println("\n[3] Signature Service")
    val signed = signatureService.sign(hashBytes, Algorithm.RSA)
    val signedEncoded = encoderService.encode(signed, EncoderType.BASE64)
    println("RSA signature (base64): $signedEncoded")

    println("\n[4] Encryption Service")
    val encrypted = encryptionService.encrypt(signed, EncrypterType.AES)
    val encryptedEncoded = encoderService.encode(encrypted, EncoderType.BASE64)
    println("AES encrypted signature (base64): $encryptedEncoded")

    val metadata = mapOf("source" to "main-integration-test")
    val requestForHash = CryptoRequest(
        data = sampleEvent,
        canonicalizerType = CanonicalizerType.BILLING_EVENT,
        hashType = HashType.SHA_256,
        encrypterType = EncrypterType.RSA,
        encoderType = EncoderType.BASE64,
        version = 1,
        metadata = metadata
    )
    val requestForSign = CryptoRequest(
        data = sampleEvent,
        canonicalizerType = CanonicalizerType.BILLING_EVENT,
        hashType = HashType.SHA_256,
        encrypterType = EncrypterType.RSA,
        encoderType = EncoderType.BASE64,
        keyId = "rsa-test-key-1",
        version = 1,
        metadata = metadata
    )
    val requestForEncrypt = CryptoRequest(
        data = sampleEvent,
        canonicalizerType = CanonicalizerType.BILLING_EVENT,
        hashType = HashType.SHA_256,
        encrypterType = EncrypterType.AES,
        encoderType = EncoderType.BASE64,
        version = 1,
        metadata = metadata
    )

    println("\n[5] DefaultCryptoService")
    val hashResponse = cryptoService.hash(requestForHash)
    val signResponse = cryptoService.sign(requestForSign)
    val encryptResponse = cryptoService.encrypt(requestForEncrypt)
    println("hash.encoded    : ${hashResponse.encoded}")
    println("sign.encoded    : ${signResponse.encoded}")
    println("encrypt.encoded : ${encryptResponse.encoded}")

    println("\n[6] StaticCryptoPipelineExecutor")
    val pipelineResponse = cryptoPipelineExecutor.execute(requestForHash)
    println("pipeline.hash   : ${pipelineResponse.hash.encoded}")
    println("pipeline.sign   : ${pipelineResponse.sign.encoded}")
    println("pipeline.encrypt: ${pipelineResponse.encrypt.encoded}")

    println("\nIntegration test run completed.")
}
