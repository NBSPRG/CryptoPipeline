class DefaultCryptoService(
    private val hashService: HashService,
    private val signatureService: SignatureService,
    private val encryptionService: EncryptionService,
    private val canonicalizerService: CanonicalizerService,
    private val encoderService: EncoderService
): CryptoService {

    override fun <T> hash(request: CryptoRequest<T>): HashResponse {
        val canonicalizedData = canonicalizerService.canonicalize(request.data, request.canonicalizerType)
        val hashedData = hashService.hash(canonicalizedData, request.hashType)
        val encodedData = encoderService.encode(hashedData, request.encoderType)

        return HashResponse(
            raw = hashedData,
            encoded = encodedData,
            hashAlgorithm = request.hashType,
            version = request.version,
            metadata = request.metadata
        )
    }

    override fun <T> sign(request: CryptoRequest<T>): SignResponse {
        val canonicalizedData = canonicalizerService.canonicalize(request.data, request.canonicalizerType)
        val hashedData = hashService.hash(canonicalizedData, request.hashType)
        val signingAlgorithm = request.encrypterType.toAlgorithm()
        val signedDocument = signatureService.sign(hashedData, signingAlgorithm)
        val encodedData = encoderService.encode(signedDocument, request.encoderType)

        return SignResponse(
            raw = signedDocument,
            encoded = encodedData,
            signatureAlgorithm = signingAlgorithm,
            version = request.version,
            metadata = request.metadata,
            keyId = request.keyId
        )
    }

    override fun <T> encrypt(request: CryptoRequest<T>): EncryptResponse {
        val canonicalizedData = canonicalizerService.canonicalize(request.data, request.canonicalizerType)
        val encryptedData = encryptionService.encrypt(canonicalizedData.toByteArray(), request.encrypterType)
        val encodedData = encoderService.encode(encryptedData, request.encoderType)

        return EncryptResponse(
            raw = encryptedData,
            encoded = encodedData,
            encryptionAlgorithm = request.encrypterType,
            version = request.version,
            metadata = request.metadata,
            iv = request.iv
        )
    }
}
