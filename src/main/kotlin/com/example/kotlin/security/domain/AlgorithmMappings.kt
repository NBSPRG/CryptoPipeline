package com.example.kotlin.security.domain

fun EncrypterType.toAlgorithm(): Algorithm {
    return when (this) {
        EncrypterType.AES -> Algorithm.AES
        EncrypterType.DES -> Algorithm.DES
        EncrypterType.RSA -> Algorithm.RSA
        EncrypterType.DESede -> Algorithm.DESede
        EncrypterType.ECDSA -> Algorithm.ECDSA
    }
}

fun Algorithm.toEncrypterType(): EncrypterType {
    return when (this) {
        Algorithm.AES -> EncrypterType.AES
        Algorithm.DES -> EncrypterType.DES
        Algorithm.RSA -> EncrypterType.RSA
        Algorithm.DESede -> EncrypterType.DESede
        Algorithm.ECDSA -> EncrypterType.ECDSA
    }
}
