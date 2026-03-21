import java.time.format.DateTimeFormatter

data class BillingEvent(
    val id: Int,
    val transactionId: String,
    val companyProduct: CompanyProduct,
    val duration: Duration,
    var hashV2: String?
): CryptoPayload {
    override fun canonicalForm(): String {
        val orderId: String = companyProduct.orderId.toString()
        val companyId: String = companyProduct.companyId
        val lineCode: String = companyProduct.lineCode.name
        val dimension: String = CanonicalDimensionNormalizer().canonicalize(companyProduct.dimension)

        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val startDate: String = duration.startDate.format(formatter)
        val endDate: String = duration.endDate.format(formatter)

        val finalString: String = """
            orderId:$orderId |
            companyId:$companyId |
            lineCode:$lineCode |
            dimension:$dimension |
            startDate:$startDate |
            endDate:$endDate
        """.trimIndent()

        return finalString
    }
}
