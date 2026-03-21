import javax.crypto.Cipher
import java.security.KeyPairGenerator

abstract class Encrypter(
    private val typeConfig: EncrypterType
) {
    abstract val type: EncrypterType

    fun encrypt(rawByte: ByteArray): ByteArray {
        val cipher = Cipher.getInstance(typeConfig.transformation)
        if (typeConfig.keyType == KeyType.SYMMETRIC) {
            val secretKey = SecretKeyProvider.generate(typeConfig.algorithm, typeConfig.keySize)
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        } else {
            val keyPairGenerator = KeyPairGenerator.getInstance(typeConfig.algorithm)
            keyPairGenerator.initialize(typeConfig.keySize)
            val publicKey = keyPairGenerator.generateKeyPair().public
            cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        }
        return cipher.doFinal(rawByte)
    }
}

class AesEncrypter: Encrypter(EncrypterType.AES) {
    override val type = EncrypterType.AES
}
class DesEncrypter: Encrypter(EncrypterType.DES) {
    override val type = EncrypterType.DES
}
class RSAEncrypter: Encrypter(EncrypterType.RSA) {
    override val type = EncrypterType.RSA
}
class DESedeEncrypter: Encrypter(EncrypterType.DESede) {
    override val type = EncrypterType.DESede
}
