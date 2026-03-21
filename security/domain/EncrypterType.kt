enum class EncrypterType(
    val algorithm: String,
    val keyType: KeyType,
    val transformation: String = algorithm,
    val keySize: Int = 256
) {
    AES("AES", KeyType.SYMMETRIC, "AES", 256),
    DES("DES", KeyType.SYMMETRIC, "DES", 56),
    RSA("RSA", KeyType.ASYMMETRIC, "RSA", 2048),
    DESede("DESede", KeyType.SYMMETRIC, "DESede", 168),
    ECDSA("EC", KeyType.ASYMMETRIC, "EC", 256)
}
