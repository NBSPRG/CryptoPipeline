import java.security.PublicKey
import javax.crypto.SecretKey

class EncryptionService(
    private val encryptionFactory: EncryptionFactory,
    private val publicKeyProvider: PublicKeyProvider? = null  
) {
    fun encrypt(message: ByteArray, type: EncrypterType): ByteArray {
        val encrypter = encryptionFactory[type]
        return when (type.keyType) {
            KeyType.SYMMETRIC -> {
                val key: SecretKey = SecretKeyProvider.getOrCreate(type.algorithm, type.keySize)
                encrypter.encrypt(message, key)
            }
            KeyType.ASYMMETRIC -> {
                val key: PublicKey = requireNotNull(publicKeyProvider) {
                    "PublicKeyProvider is required for asymmetric encryption"
                }.getKey(Algorithm.RSA)
                encrypter.encrypt(message, key)
            }
        }
    }
}