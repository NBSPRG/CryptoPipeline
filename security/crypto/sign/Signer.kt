interface Signer {
    val type: EncrypterType
    fun sign(payload: ByteArray): ByteArray
}
