import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

object SecretKeyProvider {
    private val cache = mutableMapOf<String, SecretKey>()

    fun getOrCreate(algorithm: String, keySize: Int): SecretKey {
        return cache.getOrPut(algorithm) {
            val keyGen = KeyGenerator.getInstance(algorithm)
            keyGen.init(keySize)
            keyGen.generateKey()
        }
    }
}