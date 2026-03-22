interface Verifier {
    val type: Algorithm
    fun verify(payload: ByteArray, signature: ByteArray): Boolean 
}