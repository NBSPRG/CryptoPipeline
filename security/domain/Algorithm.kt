enum class Algorithm(
    val algorithmName: String,
    val type: KeyType
) {
    AES("Aes", KeyType.SYMMETRIC),
    DES("Des", KeyType.SYMMETRIC),
    RSA("Rsa", KeyType.ASYMMETRIC),
    DESede("Desede", KeyType.SYMMETRIC),
    ECDSA("Ecdsa", KeyType.ASYMMETRIC)
}
