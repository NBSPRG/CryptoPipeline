class StaticFeatureFlagService(
    private val canonicalizationEnabled: Boolean = true,
    private val signingEnabled: Boolean = true,
    private val encryptionEnabled: Boolean = true
) : FeatureFlagService {
    override fun isCanonicalizationEnabled(): Boolean = canonicalizationEnabled

    override fun isSigningEnabled(): Boolean = signingEnabled

    override fun isEncryptionEnabled(): Boolean = encryptionEnabled
}
