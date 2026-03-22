package com.example.kotlin.security.domain

enum class HashType(val hash: String) {
    SHA_256("SHA-256"),
    SHA_512("SHA-512"),
    MD5("MD5")
}
