class DynamicCryptoPipelineExecutor(
    private val steps: List<CryptoStep>,
    private val featureFlagService: FeatureFlagService
): PipelineExecutor {

    override val type = "DYNAMIC"
    
    override fun <T> execute(request: CryptoRequest<T>): CryptoPipelineResponse {
        var cryptoContext = CryptoContext.from(request)

        for (step in steps) {
            cryptoContext = step.apply(cryptoContext, featureFlagService)
        }

        return CryptoPipelineResponse.from(cryptoContext, request)
    }
}
