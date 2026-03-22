interface PipelineExecutor {
    val type: String
    fun <T> execute(request: CryptoRequest<T>): CryptoPipelineResponse
}
