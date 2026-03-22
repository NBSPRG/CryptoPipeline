interface FeatureFlagService {
    val source: String
    fun isCanonicalizationEnabled(): Boolean
    fun isSigningEnabled(): Boolean
    fun isEncryptionEnabled(): Boolean
}
