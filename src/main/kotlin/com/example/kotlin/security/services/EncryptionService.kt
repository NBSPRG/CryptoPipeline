package com.example.kotlin.security

import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.SecretKey

class EncryptionService(
    private val encryptionFactory: EncryptionFactory,
    private val secretKeyProvider: SecretKeyProvider? = null,
    private val publicKeyProvider: PublicKeyProvider? = null,
    private val privateKeyProvider: PrivateKeyProvider? = null
) {
    fun encrypt(message: ByteArray, type: EncrypterType): ByteArray {
        val encrypter = encryptionFactory[type]
        val algorithm = type.toAlgorithm()
        return when (type.keyType) {
            KeyType.SYMMETRIC -> {
                val key: SecretKey = requireNotNull(secretKeyProvider) {
                    "SecretKeyProvider is required for symmetric encryption"
                }.getOrCreate(algorithm)
                encrypter.encrypt(message, key)
            }
            KeyType.ASYMMETRIC -> {
                val key: PublicKey = requireNotNull(publicKeyProvider) {
                    "PublicKeyProvider is required for asymmetric encryption"
                }.getOrCreate(algorithm)
                encrypter.encrypt(message, key)
            }
        }
    }

    fun decrypt(message: ByteArray, type: EncrypterType): ByteArray {
        val encrypter = encryptionFactory[type]
        val algorithm = type.toAlgorithm()
        return when (type.keyType) {
            KeyType.SYMMETRIC -> {
                val key: SecretKey = requireNotNull(secretKeyProvider) {
                    "SecretKeyProvider is required for symmetric decryption"
                }.getOrCreate(algorithm)
                encrypter.decrypt(message, key)
            }
            KeyType.ASYMMETRIC -> {
                val key: PrivateKey = requireNotNull(privateKeyProvider) {
                    "PrivateKeyProvider is required for asymmetric decryption"
                }.getOrCreate(algorithm)
                encrypter.decrypt(message, key)         
            }
        }
    }
}
