class EncryptStep(
    private val encryptionService: EncryptionService,
    private val algorithm: Algorithm
): CryptoStep {
    override fun apply(context: CryptoContext, featureFlagService: FeatureFlagService): CryptoContext =
        if (featureFlagService.isEncryptionEnabled()) {
            process(context)
        } else {
            context.copy(encrypted = context.signature ?: context.hash)
        }

    override fun process(context: CryptoContext): CryptoContext {
        val payload = requireNotNull(context.signature) { "Sign step must run before encrypt step" }
        return context.copy(
            encrypted = encryptionService.encrypt(payload, algorithm.toEncrypterType())
        )
    }
}
