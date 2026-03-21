interface CryptoStep {
    fun process(context: CryptoContext): CryptoContext
}