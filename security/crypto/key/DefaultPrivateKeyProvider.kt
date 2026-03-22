import java.security.PrivateKey

class DefaultPrivateKeyProvider : PrivateKeyProvider {
    override fun getOrCreate(algorithm: Algorithm): PrivateKey {
        return InMemoryKeyPairStore.getOrCreate(algorithm).private
    }
}
