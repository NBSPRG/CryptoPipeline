class CanonicalizerService(
    private val canonicalizerFactory: CanonicalizerFactory
) {
    @Suppress("UNCHECKED_CAST")
    fun <T> canonicalize(input: T, type: CanonicalizerType?): String {
        return if (
            input is CryptoPayload
        ) {
            input.canonicalForm()
        } else {
            requireNotNull(type) {
                "canonicalizerType is required when the payload does not implement CryptoPayload"
            }

            val canonicalizer = canonicalizerFactory[type]
            (canonicalizer as Canonicalizer<T>).canonicalize(input)
        }
    }
}
