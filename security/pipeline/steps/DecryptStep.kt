class DecryptStep(
    private val encryptionService: EncryptionService,
    private val algorithm: Algorithm
) : CryptoStep {
    override fun process(context: CryptoContext): CryptoContext {
        val payload = requireNotNull(context.encrypted) {
            "Encrypt step must run before decrypt step"
        }

        val decrypted = encryptionService.decrypt(payload, EncrypterType.valueOf(algorithm.name))
        return context.copy(signature = decrypted)
    }
}
