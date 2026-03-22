package com.example.kotlin.security.services

import com.example.kotlin.security.Canonicalizer
import com.example.kotlin.security.CryptoPayload
import com.example.kotlin.security.domain.CanonicalizerType
import com.example.kotlin.security.factory.CanonicalizerFactory

class CanonicalizerService(
    private val canonicalizerFactory: CanonicalizerFactory
) {
    @Suppress("UNCHECKED_CAST")
    fun <T> canonicalize(input: T, type: CanonicalizerType?): String {
        return if (
            input is CryptoPayload
        ) {
            input.canonicalForm()
        } else {
            requireNotNull(type) {
                "canonicalizerType is required when the payload does not implement CryptoPayload"
            }

            val canonicalizer = canonicalizerFactory[type]
            (canonicalizer as Canonicalizer<T>).canonicalize(input)
        }
    }
}
