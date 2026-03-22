interface Signer {
    val type: Algorithm
    fun sign(payload: ByteArray): ByteArray
}
