package com.example.kotlin.security.domain

enum class Algorithm(
    val keyType: KeyType,
    val transformation: String,
    val keySize: Int
) {
    AES(KeyType.SYMMETRIC, "AES", 256),
    DES(KeyType.SYMMETRIC, "DES", 56),
    RSA(KeyType.ASYMMETRIC, "RSA", 2048),
    DESede(KeyType.SYMMETRIC, "DESede", 168),
    ECDSA(KeyType.ASYMMETRIC, "EC", 256)
}
