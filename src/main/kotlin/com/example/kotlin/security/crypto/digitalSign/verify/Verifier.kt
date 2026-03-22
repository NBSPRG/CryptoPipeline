package com.example.kotlin.security

import com.example.kotlin.security.domain.Algorithm

interface Verifier {
    val type: Algorithm
    fun verify(payload: ByteArray, signature: ByteArray): Boolean 
}