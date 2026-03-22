package com.example.kotlin.security

import java.time.format.DateTimeFormatter

class DefaultBillingEventCanonicalizer: BillingEventCanonicalizer {
    override fun canonicalize(input: BillingEvent): String {
        val orderId: String = input.companyProduct.orderId.toString()
        val companyId: String = input.companyProduct.companyId
        val lineCode: String = input.companyProduct.lineCode.name
        val dimension: String = CanonicalDimensionNormalizer().canonicalize(input.companyProduct.dimension)
        
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val startDate: String = input.duration.startDate.format(formatter)
        val endDate: String = input.duration.endDate.format(formatter)

        val finalString: String = """
            orderId:$orderId |
            companyId:$companyId |
            lineCode:$lineCode |
            dimension:$dimension |
            startDate:$startDate |
            endDate:$endDate
        """.trimIndent()

        return finalString
    }
}
