import java.security.Signature

class RSASigner(
    private val privateKeyProvider: PrivateKeyProvider
): Signer {
    override val type = EncrypterType.RSA

    override fun sign(payload: ByteArray): ByteArray {
        val privateKey = privateKeyProvider.getKey(Algorithm.RSA)
        val signature = Signature.getInstance("NONEwithRSA")
        signature.initSign(privateKey)
        signature.update(payload)
        return signature.sign()
    }
}
