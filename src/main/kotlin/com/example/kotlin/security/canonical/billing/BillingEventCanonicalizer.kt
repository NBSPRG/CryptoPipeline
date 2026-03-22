package com.example.kotlin.security

interface BillingEventCanonicalizer: Canonicalizer<BillingEvent> {
    override val type: CanonicalizerType
        get() = CanonicalizerType.BILLING_EVENT
}
