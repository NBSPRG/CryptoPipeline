package com.example.kotlin.security

import java.security.PrivateKey

class DefaultPrivateKeyProvider(
    private val keyPairStore: KeyPairStore = InMemoryKeyPairStore
) : PrivateKeyProvider {
    override fun getOrCreate(algorithm: Algorithm): PrivateKey {
        return keyPairStore.getOrCreate(algorithm).private
    }
}
