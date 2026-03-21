sealed interface CryptoResponse {
    val raw: ByteArray,
    val encoded: String?,
    val version: Int,
    val metadata: Map<String, Any>
}

data class HashResponse(
    override val raw: ByteArray,
    override val encoded: String?,
    val hashAlgorithm: HashType,
    override val version: Int,
    override val metadata: Map<String, Any>
): CryptoResponse {
    companion object from(context: CryptoContext, request: CryptoRequest<T>): HashResponse {
        return HashResponse(
            raw = context.hash,
            encoded = context.encoded,
            hashAlgorithm = request.hashType,
            version = request.version,
            metadata = request.metadata
        )
    }
}

data class SignResponse(
    override val raw: ByteArray,
    override val encoded: String?,
    val signatureAlgorithm: EncrypterType,
    val keyId: String?,
    override val version: Int,
    override val metadata: Map<String, Any>
): CryptoResponse {
    companion object from(context: CryptoContext, request: CryptoRequest<T>): SignResponse {
        return SignResponse(
            raw = context.signature,
            encoded = context.encoded,
            signatureAlgorithm = request.encrypterType,
            keyId = request.keyId,
            version = request.version,
            metadata = request.metadata
        )
    }
}

data class EncryptResponse(
    override val raw: ByteArray,
    override val encoded: String?,
    val encryptionAlgorithm: EncrypterType,
    val iv: ByteArray?,
    override val version: Int,
    override val metadata: Map<String, Any>
): CryptoResponse {
    companion object from(context: CryptoContext, request: CryptoRequest<T>): EncryptResponse {
        return EncryptResponse(
            raw = context.encrypted,
            encoded = context.encoded,
            encryptionAlgorithm = request.encrypterType,
            iv = request.iv,
            version = request.version,
            metadata = request.metadata
        )
    }
}
