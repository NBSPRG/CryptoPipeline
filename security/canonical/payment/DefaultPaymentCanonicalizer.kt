class DefaultPaymentCanonicalizer: PaymentCanonicalizer {
    override fun canonicalize(input: Payment): String {
        return input.canonicalForm()
    }
}
