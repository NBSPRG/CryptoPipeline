sealed interface CryptoResponse {
    val raw: ByteArray
    val encoded: String
    val version: Int
    val metadata: Map<String, Any>
}

data class HashResponse(
    override val raw: ByteArray,
    override val encoded: String,
    val hashAlgorithm: HashType,
    override val version: Int,
    override val metadata: Map<String, Any>
): CryptoResponse

data class SignResponse(
    override val raw: ByteArray,
    override val encoded: String,
    val signatureAlgorithm: EncrypterType,
    val keyId: String?,
    override val version: Int,
    override val metadata: Map<String, Any>
): CryptoResponse

data class EncryptResponse(
    override val raw: ByteArray,
    override val encoded: String,
    val encryptionAlgorithm: EncrypterType,
    val iv: ByteArray?,
    override val version: Int,
    override val metadata: Map<String, Any>
): CryptoResponse
