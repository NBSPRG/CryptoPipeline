package com.example.kotlin.security

import com.example.kotlin.security.domain.Algorithm

interface KeyProvider<K> {
    fun getOrCreate(algorithm: Algorithm): K
}
