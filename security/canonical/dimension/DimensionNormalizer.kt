interface DimensionNormalizer: Canonicalizer<Map<String, String>> {
    override val type: CanonicalizerType
        get() = CanonicalizerType.DIMENSION
}
