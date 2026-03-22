data class BillingEvent(
    val id: Int,
    val transactionId: String,
    val companyProduct: CompanyProduct,
    val duration: Duration,
    var hashV2: String?
): CryptoPayload {
    override fun canonicalForm(): String =
        DefaultBillingEventCanonicalizer().canonicalize(this)
}