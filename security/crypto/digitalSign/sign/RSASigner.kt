import java.security.Signature

class RSASigner(
    private val privateKeyProvider: PrivateKeyProvider
) : Signer {
    override val type = Algorithm.RSA

    override fun sign(payload: ByteArray): ByteArray {
        val privateKey = privateKeyProvider.getOrCreate(type)
        val signature = Signature.getInstance("NONEwithRSA")
        signature.initSign(privateKey)
        signature.update(payload)
        return signature.sign()
    }
}
