class DefaultStringCanonicalizer: StringCanonicalizer {
    override fun canonicalize(input: String): String {
        return "input:${(input.trim().uppercase())}"
    }
}