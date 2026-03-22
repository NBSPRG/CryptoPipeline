package com.example.kotlin.security

data class CryptoPipelineResponse(
    val hash: HashResponse,
    val encrypt: EncryptResponse,
    val sign: SignResponse
) {
    companion object {
        fun <T> from(context: CryptoContext, request: CryptoRequest<T>): CryptoPipelineResponse {
            return CryptoPipelineResponse(
                hash = HashResponse.from(context, request),
                encrypt = EncryptResponse.from(context, request),
                sign = SignResponse.from(context, request)
            )
        }
    }
}
