class EncryptStep(
    private val encryptionService: EncryptionService,
    private val encrypter: Encrypter
): CryptoStep {
    override val process(context: CryptoContext): CryptoContext {
        context.encrypted = encryptionService.encrypt(context.signature!!, encrypter)
        return context
    }
}