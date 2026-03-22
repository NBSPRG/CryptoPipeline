sealed interface CryptoResponse {
    val raw: ByteArray
    val encoded: String?
    val version: Int
    val metadata: Map<String, Any>
}

data class HashResponse(
    override val raw: ByteArray,
    override val encoded: String?,
    val hashAlgorithm: HashType,
    override val version: Int,
    override val metadata: Map<String, Any>
): CryptoResponse {
    companion object {
        fun <T> from(context: CryptoContext, request: CryptoRequest<T>): HashResponse {
            return HashResponse(
                raw = requireNotNull(context.hash) { "Hash result is missing from crypto context" },
                encoded = context.encoded,
                hashAlgorithm = request.hashType,
                version = request.version,
                metadata = request.metadata
            )
        }
    }
}

data class SignResponse(
    override val raw: ByteArray,
    override val encoded: String?,
    val signatureAlgorithm: Algorithm,
    val keyId: String?,
    override val version: Int,
    override val metadata: Map<String, Any>
): CryptoResponse {
    companion object {
        fun <T> from(context: CryptoContext, request: CryptoRequest<T>): SignResponse {
            return SignResponse(
                raw = requireNotNull(context.signature) { "Signature result is missing from crypto context" },
                encoded = context.encoded,
                signatureAlgorithm = request.encrypterType.toAlgorithm(),
                keyId = request.keyId,
                version = request.version,
                metadata = request.metadata
            )
        }
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
    companion object {
        fun <T> from(context: CryptoContext, request: CryptoRequest<T>): EncryptResponse {
            return EncryptResponse(
                raw = requireNotNull(context.encrypted) { "Encrypted result is missing from crypto context" },
                encoded = context.encoded,
                encryptionAlgorithm = request.encrypterType,
                iv = request.iv,
                version = request.version,
                metadata = request.metadata
            )
        }
    }
}
