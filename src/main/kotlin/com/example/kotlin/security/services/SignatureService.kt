package com.example.kotlin.security.services

import com.example.kotlin.security.Signer
import com.example.kotlin.security.domain.Algorithm
import com.example.kotlin.security.domain.KeyType

class SignatureService(
    private val signer: Signer
) {
    fun sign(document: ByteArray, type: Algorithm): ByteArray {
        require(type.keyType == KeyType.ASYMMETRIC) {
            "Signature is only supported with asymmetric algorithms"
        }
        require(signer.type == type) {
            "Signer for type ${signer.type} cannot handle $type"
        }
        return signer.sign(document)
    }
}
