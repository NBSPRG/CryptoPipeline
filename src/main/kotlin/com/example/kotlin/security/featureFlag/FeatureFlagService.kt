package com.example.kotlin.security

interface FeatureFlagService {
    val source: String
    fun isCanonicalizationEnabled(): Boolean
    fun isSigningEnabled(): Boolean
    fun isEncryptionEnabled(): Boolean
}
