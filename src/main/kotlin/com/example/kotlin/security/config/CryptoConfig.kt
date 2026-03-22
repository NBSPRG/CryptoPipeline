package com.example.kotlin.security

import com.example.kotlin.security.domain.EncoderType
import com.example.kotlin.security.domain.EncrypterType
import com.example.kotlin.security.domain.HashType

data class CryptoConfig(
    val defaultHash: HashType,
    val defaultEncrypter: EncrypterType,
    val defaultEncoder: EncoderType,
    val defaultSigner: Signer
)
