package com.example.kotlin.security

interface KeyProvider<K> {
    fun getOrCreate(algorithm: Algorithm): K
}
