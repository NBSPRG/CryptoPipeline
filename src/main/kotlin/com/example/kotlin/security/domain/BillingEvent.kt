package com.example.kotlin.security

data class BillingEvent(
    val id: Int,
    val transactionId: String,
    val companyProduct: CompanyProduct,
    val duration: Duration,
    var hashV2: String?
)
