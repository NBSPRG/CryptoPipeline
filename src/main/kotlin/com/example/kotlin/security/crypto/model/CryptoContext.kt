package com.example.kotlin.security

data class CryptoContext(
    val inputData: Any,
    val rawData: String,
    val canonicalData: String? = null,
    val hash: ByteArray? = null,
    val signature: ByteArray? = null,
    val encrypted: ByteArray? = null,
    val encoded: String? = null
) {
    companion object {
        fun <T> from(request: CryptoRequest<T>): CryptoContext {
            return CryptoContext(
                inputData = request.data as Any,
                rawData = request.data.toString()
            )
        }
    }
}
