package com.example.kotlin.security

import com.example.kotlin.security.domain.Algorithm
import java.security.Signature

class RSAVerifier(
    private val publicKeyProvider: PublicKeyProvider
) : Verifier {
    override val type = Algorithm.RSA

    override fun verify(payload: ByteArray, signature: ByteArray): Boolean {
        val publicKey = publicKeyProvider.getOrCreate(type)
        val sig = Signature.getInstance("NONEwithRSA")
        sig.initVerify(publicKey)
        sig.update(payload)

        return sig.verify(signature)
    }
}
