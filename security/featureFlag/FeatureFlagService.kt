interface FeatureFlagService {
    fun isCanonicalizationEnabled(): Boolean
    fun isSigningEnabled(): Boolean
    fun isEncryptionEnabled(): Boolean
}