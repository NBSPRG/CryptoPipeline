package com.example.kotlin.security

import com.example.kotlin.security.domain.Algorithm

interface Signer {
    val type: Algorithm
    fun sign(payload: ByteArray): ByteArray
}
