class EncoderService(
    private val factory: EncoderFactory
) {
    fun encode(data: ByteArray, type: EncoderType): String {
        val encoder = factory[type]
        return encoder.encode(data)
    }
}
