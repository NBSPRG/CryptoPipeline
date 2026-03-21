import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

object SecretKeyProvider {
    fun generate(algorithm: String, keySize: Int): SecretKey {
        val keyGen = KeyGenerator.getInstance(algorithm)
        keyGen.init(keySize)
        return keyGen.generateKey()
    }
}
