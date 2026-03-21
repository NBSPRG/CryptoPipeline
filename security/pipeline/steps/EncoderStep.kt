class EncoderStep(
    private val encoderService: EncoderService,
    private val encoderType: EncoderType
): CryptoStep {
    override fun process(context: CryptoContext): CryptoContext {
        context.encoded = encoderService.encode(context, encoderType)
        return context
    }
}