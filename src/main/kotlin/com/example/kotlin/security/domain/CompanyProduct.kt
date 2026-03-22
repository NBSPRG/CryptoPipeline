package com.example.kotlin.security

data class CompanyProduct(
    val orderId: Int,
    val companyId: String,
    val lineCode: LineCode,
    val dimension: Map<String, String>
)