package com.example.kotlin.security

import java.security.KeyPair

interface KeyPairStore {
    fun getOrCreate(algorithm: Algorithm): KeyPair
}
