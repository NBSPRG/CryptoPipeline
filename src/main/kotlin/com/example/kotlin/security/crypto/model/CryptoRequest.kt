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
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CryptoRequest<*>

        if (version != other.version) return false
        if (data != other.data) return false
        if (canonicalizerType != other.canonicalizerType) return false
        if (hashType != other.hashType) return false
        if (encrypterType != other.encrypterType) return false
        if (encoderType != other.encoderType) return false
        if (keyId != other.keyId) return false
        if (!iv.contentEquals(other.iv)) return false
        if (metadata != other.metadata) return false

        return true
    }

    override fun hashCode(): Int {
        var result = version
        result = 31 * result + (data?.hashCode() ?: 0)
        result = 31 * result + (canonicalizerType?.hashCode() ?: 0)
        result = 31 * result + hashType.hashCode()
        result = 31 * result + encrypterType.hashCode()
        result = 31 * result + encoderType.hashCode()
        result = 31 * result + (keyId?.hashCode() ?: 0)
        result = 31 * result + (iv?.contentHashCode() ?: 0)
        result = 31 * result + metadata.hashCode()
        return result
    }
}
