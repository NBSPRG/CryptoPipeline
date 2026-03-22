package com.example.kotlin.security

interface DimensionNormalizer: Canonicalizer<Map<String, String>> {
    override val type: CanonicalizerType
        get() = CanonicalizerType.DIMENSION
}
