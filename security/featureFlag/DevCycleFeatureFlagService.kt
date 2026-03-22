import com.devcycle.sdk.server.common.model.User
import com.devcycle.sdk.server.local.api.DVCLocalClient

class DevCycleFeatureFlagService(
    private val client: DVCLocalClient,
    private val user: User
) : FeatureFlagService {
    override val source: String = "DevCycle"

    override fun isCanonicalizationEnabled(): Boolean =
        client.variableValue(user, "enable-canonicalization", false)

    override fun isSigningEnabled(): Boolean =
        client.variableValue(user, "enable-sigining", false)

    override fun isEncryptionEnabled(): Boolean =
        client.variableValue(user, "enable-encryption", false)
}
