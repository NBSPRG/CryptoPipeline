package com.example.kotlin.security

interface PaymentCanonicalizer: Canonicalizer<Payment> {
    override val type: CanonicalizerType
        get() = CanonicalizerType.PAYMENT
}
