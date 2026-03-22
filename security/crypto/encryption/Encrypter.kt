import javax.crypto.Cipher
import javax.crypto.SecretKey
import java.security.PublicKey
import java.security.KeyPairGenerator

abstract class Encrypter {
    abstract val type: EncrypterType

    fun encrypt(rawByte: ByteArray, secretKey: SecretKey): ByteArray {
        val cipher = Cipher.getInstance(type.transformation)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        return cipher.doFinal(rawByte)
    }

    fun encrypt(rawByte: ByteArray, publicKey: PublicKey): ByteArray {
        val cipher = Cipher.getInstance(type.transformation)
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        return cipher.doFinal(rawByte)
    }
}

class AesEncrypter : Encrypter() { override val type = EncrypterType.AES }
class DesEncrypter : Encrypter() { override val type = EncrypterType.DES }
class RSAEncrypter : Encrypter() { override val type = EncrypterType.RSA }
class DESedeEncrypter : Encrypter() { override val type = EncrypterType.DESede }