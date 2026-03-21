class DefaultPaymentCanonicalizer: PaymentCanonicalizer {
    override fun canonicalize(input: Payment): String {
        return "amount:${input.amount}|userId:${input.userId}"
    }
}
