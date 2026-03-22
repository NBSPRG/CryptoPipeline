package com.example.kotlin.security

import com.example.kotlin.security.domain.CanonicalizerType

interface DimensionNormalizer: Canonicalizer<Map<String, String>> {
    override val type: CanonicalizerType
        get() = CanonicalizerType.DIMENSION
}
