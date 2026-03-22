package com.example.kotlin.security

data class CryptoConfig(
    val defaultHash: HashType,
    val defaultEncrypter: EncrypterType,
    val defaultEncoder: EncoderType,
    val defaultSigner: Signer
)
