class CanonicalizeStep(
    private val canonicalizerService: CanonicalizerService,
    private val canonicalizerType: CanonicalizerType
): CryptoStep {
    override fun process(context: CryptoContext): CryptoContext {
        return context.copy(
            canonicalData = canonicalizerService.canonicalize(context.inputData, canonicalizerType)
        )
    }
}
