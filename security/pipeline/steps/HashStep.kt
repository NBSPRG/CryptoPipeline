class HashStep(
    private val hashService: HashService,
    private val hashType: HashType
): CryptoContext {
    override fun process(context: CryptoContext): CryptoContext {
        context.hash = hashService.hash(context.rawData, hashType)
        return context
    }
}