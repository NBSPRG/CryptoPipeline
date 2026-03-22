package com.example.kotlin.security

import com.example.kotlin.security.domain.Algorithm
import java.security.KeyPair

interface KeyPairStore {
    fun getOrCreate(algorithm: Algorithm): KeyPair
}
