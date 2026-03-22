package com.example.kotlin.security.featureFlag

interface FeatureFlagService {
    val source: String
    fun isCanonicalizationEnabled(): Boolean
    fun isSigningEnabled(): Boolean
    fun isEncryptionEnabled(): Boolean
}
