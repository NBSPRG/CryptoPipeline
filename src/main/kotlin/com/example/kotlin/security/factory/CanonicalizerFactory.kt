package com.example.kotlin.security.factory

import com.example.kotlin.security.Canonicalizer
import com.example.kotlin.security.domain.CanonicalizerType

class CanonicalizerFactory(
    canonicalizer: List<Canonicalizer<*>>
) {
    private val registry: Map<CanonicalizerType, Canonicalizer<*>> =
        canonicalizer.associateBy { it.type }
    
    operator fun get(type: CanonicalizerType): Canonicalizer<*> {
        return registry[type]
            ?: error("No canonicalizer for type: $type")
    }
}
