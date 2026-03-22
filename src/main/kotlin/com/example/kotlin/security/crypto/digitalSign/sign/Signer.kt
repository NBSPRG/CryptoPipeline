package com.example.kotlin.security

interface Signer {
    val type: Algorithm
    fun sign(payload: ByteArray): ByteArray
}
