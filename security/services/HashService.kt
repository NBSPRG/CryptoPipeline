class HashService(
    private val hashFactory: HashFactory
) {
    fun hash(input: String, type: HashType): ByteArray {
        val hasher = hashFactory[type]
        return hasher.hash(input)
    }
}