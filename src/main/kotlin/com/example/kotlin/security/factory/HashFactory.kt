package com.example.kotlin.security

import com.example.kotlin.security.domain.HashType
import com.example.kotlin.security.hash.HashGenerator

class HashFactory(
    hash: List<HashGenerator>
) {
    private val registry: Map<HashType, HashGenerator> =
        hash.associateBy { it.type }

    operator fun get(type: HashType): HashGenerator {
        return registry[type]
            ?: error("No hashgenerator for type: $type")
    }
}
