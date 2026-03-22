import java.util.concurrent.ConcurrentHashMap
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class DefaultSecretKeyProvider : SecretKeyProvider {
    private val cache = ConcurrentHashMap<Algorithm, SecretKey>()

    override fun getOrCreate(algorithm: Algorithm): SecretKey {
        return cache.getOrPut(algorithm) {
            val keyGen = KeyGenerator.getInstance(algorithm.name)
            keyGen.init(algorithm.keySize)
            keyGen.generateKey()
        }
    }
}
