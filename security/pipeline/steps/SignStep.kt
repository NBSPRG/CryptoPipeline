class SignStep(
    private val signatureService: SignatureService,
    private val signer: Signer
) {
    override fun process(context: CryptoContext): CryptoContext {
        context.signature = signatureService.sign(context.hash!!, signer.type)
        return context
    }
}