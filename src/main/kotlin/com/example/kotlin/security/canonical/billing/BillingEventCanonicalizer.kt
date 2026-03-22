package com.example.kotlin.security

import com.example.kotlin.security.domain.BillingEvent
import com.example.kotlin.security.domain.CanonicalizerType

interface BillingEventCanonicalizer: Canonicalizer<BillingEvent> {
    override val type: CanonicalizerType
        get() = CanonicalizerType.BILLING_EVENT
}
