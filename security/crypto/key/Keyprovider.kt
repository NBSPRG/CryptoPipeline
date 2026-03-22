interface KeyProvider<K> {
    fun getOrCreate(algorithm: Algorithm): K
}
