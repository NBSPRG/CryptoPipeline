package com.example.kotlin.security

import java.security.MessageDigest

abstract class HashGenerator(
    private val algorithm: String
) {
    abstract val type: HashType

    fun hash(input: String): ByteArray {
        val digest = MessageDigest.getInstance(algorithm)
        return digest.digest(input.toByteArray())
    }
}

class Sha256HashGenerator: HashGenerator("SHA-256") {
    override val type = HashType.SHA_256
}
class Sha512HashGenerator: HashGenerator("SHA-512") {
    override val type = HashType.SHA_512
}
class MD5HashGenerator: HashGenerator("MD5") {
    override val type = HashType.MD5
}
