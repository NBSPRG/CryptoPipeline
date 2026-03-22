data class FeatureFlagConfig(
    val serverKey: String?,
    val userId: String?
) {
    companion object {
        fun fromEnv(
            serverKeyEnv: String = "SERVER_KEY",
            userIdEnv: String = "USER_ID"
        ): FeatureFlagConfig? {
            val serverKey = System.getenv(serverKeyEnv)?.ifBlank { null }
            val userId = System.getenv(userIdEnv)?.ifBlank { null }
            if (serverKey == null && userId == null) {
                return null
            }

            return FeatureFlagConfig(
                serverKey = serverKey,
                userId = userId
            )
        }

        fun load(resourceName: String = "config.yml"): FeatureFlagConfig? {
            val stream = FeatureFlagConfig::class.java.classLoader
                .getResourceAsStream(resourceName)
                ?: return null

            val values = linkedMapOf<String, String>()
            stream.bufferedReader().useLines { lines ->
                lines.forEach { line ->
                    val trimmed = line.trim()
                    if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                        return@forEach
                    }

                    val separatorIndex = trimmed.indexOf(':')
                    if (separatorIndex <= 0) {
                        return@forEach
                    }

                    val key = trimmed.substring(0, separatorIndex).trim()
                    val value = trimmed.substring(separatorIndex + 1)
                        .trim()
                        .trim('"')
                        .trim('\'')
                    values[key] = value
                }
            }

            return FeatureFlagConfig(
                serverKey = values["server_key"]?.ifBlank { null },
                userId = values["user_id"]?.ifBlank { null }
            )
        }

        fun resolve(
            resourceName: String = "config.yml",
            serverKeyEnv: String = "SERVER_KEY",
            userIdEnv: String = "USER_ID"
        ): FeatureFlagConfig? {
            val envConfig = fromEnv(serverKeyEnv, userIdEnv)
            if (!envConfig?.serverKey.isNullOrBlank()) {
                return envConfig
            }

            val fileConfig = load(resourceName)
            if (!fileConfig?.serverKey.isNullOrBlank()) {
                return fileConfig
            }

            return null
        }
    }
}
