package com.example.kotlin.security.factory

import com.example.kotlin.security.Encrypter
import com.example.kotlin.security.domain.EncrypterType

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
