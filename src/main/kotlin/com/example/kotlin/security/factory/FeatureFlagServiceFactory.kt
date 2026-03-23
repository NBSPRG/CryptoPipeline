package com.example.kotlin.security.factory

import com.devcycle.sdk.server.common.model.User
import com.devcycle.sdk.server.local.api.DVCLocalClient
import com.devcycle.sdk.server.local.model.DVCLocalOptions
import com.example.kotlin.security.featureFlag.DevCycleFeatureFlagService
import com.example.kotlin.security.FeatureFlagConfig
import com.example.kotlin.security.featureFlag.FeatureFlagService
import com.example.kotlin.security.featureFlag.StaticFeatureFlagService

object FeatureFlagServiceFactory {
    private const val SERVER_SDK_KEY_ENV = "SERVER_KEY"
    private const val USER_ID_ENV = "USER_ID"
    private const val DEFAULT_USER_ID = "crypto-pipeline-service"
    private const val CONFIG_FILE_NAME = "application.yml"
    private const val INIT_TIMEOUT_MS = 10_000L
    private const val INIT_POLL_INTERVAL_TIME_MS = 100L

    fun create(): FeatureFlagService {
        val resolvedConfig = FeatureFlagConfig.Companion.resolve(
            resourceName = CONFIG_FILE_NAME,
            serverKeyEnv = SERVER_SDK_KEY_ENV,
            userIdEnv = USER_ID_ENV
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
            .userId(resolvedConfig.userId ?: DEFAULT_USER_ID)
            .build()

        return DevCycleFeatureFlagService(client, user)
    }

    private fun waitForInitialization(client: DVCLocalClient) {
        val deadline = System.currentTimeMillis() + INIT_TIMEOUT_MS
        while (!client.isInitialized && System.currentTimeMillis() < deadline) {
            Thread.sleep(INIT_POLL_INTERVAL_TIME_MS)
        }
    }
}
