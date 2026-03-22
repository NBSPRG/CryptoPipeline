package com.example.kotlin.security

import com.devcycle.sdk.server.common.model.User
import com.devcycle.sdk.server.local.api.DVCLocalClient
import com.devcycle.sdk.server.local.model.DVCLocalOptions

object FeatureFlagServiceFactory {
    private const val serverSdkKeyEnv = "SERVER_KEY"
    private const val userIdEnv = "USER_ID"
    private const val defaultUserId = "crypto-pipeline-service"
    private const val configFileName = "config.yml"
    private const val initTimeoutMs = 10_000L
    private const val initPollIntervalMs = 100L

    fun create(): FeatureFlagService {
        val resolvedConfig = FeatureFlagConfig.resolve(
            resourceName = configFileName,
            serverKeyEnv = serverSdkKeyEnv,
            userIdEnv = userIdEnv
        )
        if (resolvedConfig == null) {
            return StaticFeatureFlagService()
        }
        val serverSdkKey = resolvedConfig.serverKey

        val client = DVCLocalClient(
            serverSdkKey,
            DVCLocalOptions.builder().build()
        )
        waitForInitialization(client)
        val user = User.builder()
            .userId(resolvedConfig.userId ?: defaultUserId)
            .build()

        return DevCycleFeatureFlagService(client, user)
    }

    private fun waitForInitialization(client: DVCLocalClient) {
        val deadline = System.currentTimeMillis() + initTimeoutMs
        while (!client.isInitialized() && System.currentTimeMillis() < deadline) {
            Thread.sleep(initPollIntervalMs)
        }
    }
}
