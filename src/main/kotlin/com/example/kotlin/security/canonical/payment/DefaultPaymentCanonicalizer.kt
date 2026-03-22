package com.example.kotlin.security

import com.example.kotlin.security.domain.Payment

class DefaultPaymentCanonicalizer: PaymentCanonicalizer {
    override fun canonicalize(input: Payment): String {
        return input.canonicalForm()
    }
}
