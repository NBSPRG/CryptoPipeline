package com.example.kotlin.security.domain

data class BillingEvent(
    val id: Int,
    val transactionId: String,
    val companyProduct: CompanyProduct,
    val duration: Duration,
    var hashV2: String?
)
