class CanonicalizeStep(
    private val canonicalizerService: CanonicalizerService,
    private val canonicalizerType: CanonicalizerType
): CryptoStep {
    override fun process(context: CryptoContext): CryptoContext {
        context.canonicalData = canonicalizer.canonicalize(context.rawData)
        return context
    }
}