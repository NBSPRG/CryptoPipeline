import java.security.KeyPair
import java.security.KeyPairGenerator
import java.util.concurrent.ConcurrentHashMap

object InMemoryKeyPairStore {
    private val cache = ConcurrentHashMap<Algorithm, KeyPair>()

    fun getOrCreate(algorithm: Algorithm): KeyPair {
        require(algorithm.keyType == KeyType.ASYMMETRIC) {
            "Key pairs are only supported for asymmetric algorithms"
        }

        return cache.getOrPut(algorithm) {
            val keyPairGen = KeyPairGenerator.getInstance(algorithm.transformation)
            keyPairGen.initialize(algorithm.keySize)
            keyPairGen.generateKeyPair()
        }
    }
}
