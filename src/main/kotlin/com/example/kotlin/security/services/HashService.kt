package com.example.kotlin.security.services

import com.example.kotlin.security.HashFactory
import com.example.kotlin.security.domain.HashType

class HashService(
    private val hashFactory: HashFactory
) {
    fun hash(input: String, type: HashType): ByteArray {
        val hasher = hashFactory[type]
        return hasher.hash(input)
    }
}