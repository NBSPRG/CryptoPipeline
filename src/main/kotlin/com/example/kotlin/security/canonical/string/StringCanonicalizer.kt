package com.example.kotlin.security

interface StringCanonicalizer: Canonicalizer<String> {
    override val type: CanonicalizerType
        get() = CanonicalizerType.STRING
}
