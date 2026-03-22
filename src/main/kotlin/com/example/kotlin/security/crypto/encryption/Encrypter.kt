package com.example.kotlin.security

import javax.crypto.Cipher
import javax.crypto.SecretKey
import java.security.PrivateKey
import java.security.PublicKey

abstract class Encrypter {
    abstract val type: EncrypterType

    fun encrypt(rawByte: ByteArray, secretKey: SecretKey): ByteArray {
        val cipher = Cipher.getInstance(type.transformation)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        return cipher.doFinal(rawByte)
    }

    fun encrypt(rawByte: ByteArray, publicKey: PublicKey): ByteArray {
        val cipher = Cipher.getInstance(type.transformation)
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        return cipher.doFinal(rawByte)
    }

    fun decrypt(encrypted: ByteArray, secretKey: SecretKey): ByteArray {
        val cipher = Cipher.getInstance(type.transformation)
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        return cipher.doFinal(encrypted)
    }

    fun decrypt(encrypted: ByteArray, privateKey: PrivateKey): ByteArray {
        val cipher = Cipher.getInstance(type.transformation)
        cipher.init(Cipher.DECRYPT_MODE, privateKey)
        return cipher.doFinal(encrypted)
    }
}

class AesEncrypter : Encrypter() { override val type = EncrypterType.AES }
class DesEncrypter : Encrypter() { override val type = EncrypterType.DES }
class RSAEncrypter : Encrypter() { override val type = EncrypterType.RSA }
class DESedeEncrypter : Encrypter() { override val type = EncrypterType.DESede }
