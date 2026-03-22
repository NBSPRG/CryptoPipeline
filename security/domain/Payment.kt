data class Amount(
    val price: Double,
    val currency: Currency
)

data class Payment(
    val amount: Amount,
    val userId: String
): CryptoPayload {

    override fun canonicalForm(): String {
        return "amount:${amount}|userId:${userId}"
    }
}
