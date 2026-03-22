class EncryptStep(
    private val encryptionService: EncryptionService,
    private val algorithm: Algorithm
): CryptoStep {
    override fun process(context: CryptoContext): CryptoContext {
        val payload = requireNotNull(context.signature) { "Sign step must run before encrypt step" }
        return context.copy(
            encrypted = encryptionService.encrypt(payload, EncrypterType.valueOf(algorithm.name))
        )
    }
}
