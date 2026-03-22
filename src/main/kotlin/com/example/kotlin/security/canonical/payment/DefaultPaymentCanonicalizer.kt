package com.example.kotlin.security

class DefaultPaymentCanonicalizer: PaymentCanonicalizer {
    override fun canonicalize(input: Payment): String {
        return input.canonicalForm()
    }
}
