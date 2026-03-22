import java.security.PublicKey

class DefaultPublicKeyProvider : PublicKeyProvider {
    override fun getOrCreate(algorithm: Algorithm): PublicKey {
        return InMemoryKeyPairStore.getOrCreate(algorithm).public
    }
}
