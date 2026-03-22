package com.example.kotlin.security.domain

data class CompanyProduct(
    val orderId: Int,
    val companyId: String,
    val lineCode: LineCode,
    val dimension: Map<String, String>
)