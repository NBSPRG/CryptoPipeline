package com.example.kotlin.security

import com.example.kotlin.security.domain.CanonicalizerType
import com.example.kotlin.security.domain.Payment

interface PaymentCanonicalizer: Canonicalizer<Payment> {
    override val type: CanonicalizerType
        get() = CanonicalizerType.PAYMENT
}
