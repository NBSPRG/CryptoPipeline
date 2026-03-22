package com.example.kotlin.security

class EncryptionFactory(
    encrypter: List<Encrypter>
) {
    private val registry: Map<EncrypterType, Encrypter> = 
        encrypter.associateBy { it.type }

    operator fun get(type: EncrypterType): Encrypter {
        return registry[type]
            ?: error("No encrypter for type: $type")
    }
}
