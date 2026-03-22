class StaticCryptoPipelineExecutor(
    private val config: CryptoConfig,
    private val featureFlagService: FeatureFlagService,
    private val canonicalizerService: CanonicalizerService,
    private val hashService: HashService,
    private val signatureService: SignatureService,
    private val encryptionService: EncryptionService,
    private val encoderService: EncoderService
): PipelineExecutor {
    
    override val type = "STATIC"
    
    override fun <T> execute(request: CryptoRequest<T>): CryptoPipelineResponse {

        val canonicalData =
            if (featureFlagService.isCanonicalizationEnabled() && request.canonicalizerType != null)
                canonicalizerService.canonicalize(request.data, request.canonicalizerType)
            else request.data.toString()

        val hash = hashService.hash(canonicalData, config.defaultHash)
        val hashEncoded = encoderService.encode(hash, config.defaultEncoder)

        val signature =
            if (featureFlagService.isSigningEnabled())
                signatureService.sign(hash, config.defaultSigner.type)
            else hash
        val signatureEncoded = encoderService.encode(signature, config.defaultEncoder)

        val encrypted =
            if (featureFlagService.isEncryptionEnabled())
                encryptionService.encrypt(signature, config.defaultEncrypter)
            else signature
        val encryptedEncoded = encoderService.encode(encrypted, config.defaultEncoder)

        return CryptoPipelineResponse(
            hash = HashResponse(
                raw = hash,
                encoded = hashEncoded,
                hashAlgorithm = config.defaultHash,
                version = request.version,
                metadata = request.metadata
            ),
            sign = SignResponse(
                raw = signature,
                encoded = signatureEncoded,
                signatureAlgorithm = config.defaultSigner.type,
                keyId = null,
                version = request.version,
                metadata = request.metadata
            ),
            encrypt = EncryptResponse(
                raw = encrypted,
                encoded = encryptedEncoded,
                encryptionAlgorithm = config.defaultEncrypter,
                iv = null,
                version = request.version,
                metadata = request.metadata
            )
        )
    }
}
