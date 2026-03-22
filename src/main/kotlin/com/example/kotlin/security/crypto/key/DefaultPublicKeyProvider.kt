package com.example.kotlin.security

import java.security.PublicKey

class DefaultPublicKeyProvider(
    private val keyPairStore: KeyPairStore = InMemoryKeyPairStore
) : PublicKeyProvider {
    override fun getOrCreate(algorithm: Algorithm): PublicKey {
        return keyPairStore.getOrCreate(algorithm).public
    }
}
