class DynamicCryptoPipelineExecutor(
    private val steps: List<CryptoStep>
): PipelineExecutor {

    override val type = "DYNAMIC"
    
    override fun <T> execute(request: CryptoRequest<T>): CryptoPipelineResponse {
        var cryptoContext = CryptoContext.from(request)

        for (step in steps) {
            cryptoContext = step.process(cryptoContext)
        }

        return CryptoPipelineResponse.from(cryptoContext, request)
    }
}
