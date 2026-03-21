interface StringCanonicalizer: Canonicalizer<String> {
    override val type: CanonicalizerType
        get() = CanonicalizerType.STRING
}
