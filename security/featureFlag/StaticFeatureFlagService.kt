class StaticFeatureFlagService : FeatureFlagService {
    override val source: String = "StaticFallback"

    override fun isCanonicalizationEnabled(): Boolean = true

    override fun isSigningEnabled(): Boolean = true

    override fun isEncryptionEnabled(): Boolean = true
}
