package com.example.kotlin.security

import com.example.kotlin.security.domain.CanonicalizerType
import com.example.kotlin.security.domain.EncoderType
import com.example.kotlin.security.domain.EncrypterType
import com.example.kotlin.security.domain.HashType

data class CryptoRequest<T>(
    val data: T,
    val canonicalizerType: CanonicalizerType? = null,
    val hashType: HashType,
    val encrypterType: EncrypterType,
    val encoderType: EncoderType,
    val keyId: String? = null,
    val iv: ByteArray? = null,
    val version: Int,
    val metadata: Map<String, Any> = emptyMap()
)
