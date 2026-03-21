interface BillingEventCanonicalizer: Canonicalizer<BillingEvent> {
    override val type: CanonicalizerType
        get() = CanonicalizerType.BILLING_EVENT
}
