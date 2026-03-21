interface KeyProvider<K> {
    fun getKey(algorithm: Algorithm): K
}