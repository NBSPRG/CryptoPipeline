data class CryptoConfig(
    val defaultHash: HashType,
    val defaultEncrypter: EncrypterType,
    val defaultEncoder: EncoderType,
    val defaultSigner: Signer,
    val enableSigning: Boolean = true,
    val enableEncryption: Boolean = true,
    val enableCanonicalization: Boolean 
)