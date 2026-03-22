package com.example.kotlin.security

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
