import dev.openfeature.sdk.Client

class OpenFeatureFlagService(
    private val client: Client
) : FeatureFlagService {
    override fun isCanonicalizationEnabled() =
        client.getBooleanValue("enable-canonicalization", false)

    override fun isSigningEnabled() =
        client.getBooleanValue("enable-signing", true)

    override fun isEncryptionEnabled() =
        client.getBooleanValue("enable-encryption", true)
}
