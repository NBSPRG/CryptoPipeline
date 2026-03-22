package com.example.kotlin.security

import com.example.kotlin.security.domain.CanonicalizerType

interface StringCanonicalizer: Canonicalizer<String> {
    override val type: CanonicalizerType
        get() = CanonicalizerType.STRING
}
