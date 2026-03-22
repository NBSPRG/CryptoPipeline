package com.example.kotlin.security

interface CryptoPayload {
    fun canonicalForm(): String
}