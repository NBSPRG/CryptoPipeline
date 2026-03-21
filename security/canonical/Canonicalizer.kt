interface Canonicalizer<T> {
    val type: CanonicalizerType
    fun canonicalize(input: T): String 
}