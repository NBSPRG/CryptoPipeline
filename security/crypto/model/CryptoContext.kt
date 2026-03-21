data class CryptoContext(
    val rawData: String, 
    val canonicalData: String? = null,
    val hash: ByteArray? = null,
    val signature: ByteArray? = null,
    val encrypted: ByteArray? = null,
    val encoded: ByteArray? = null
) {
    companion object {
        fun <T> from(request: CryptoRequest<T>): CryptoContext {
            return CryptoContext(
                data = request.data.toString()
            )
        }
    }
}
