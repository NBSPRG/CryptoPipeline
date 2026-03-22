import java.security.Signature

class RSASigner(
    private val privateKeyProvider: PrivateKeyProvider
) : Signer {
    override val type = Algorithm.RSA

    override fun sign(payload: ByteArray): ByteArray {
        val privateKey = privateKeyProvider.getOrCreate(type)
        // We intentionally use NONEwithRSA here, so the caller must hash the payload before signing.
        val signature = Signature.getInstance("NONEwithRSA")
        signature.initSign(privateKey)
        signature.update(payload)
        return signature.sign()
    }
}
