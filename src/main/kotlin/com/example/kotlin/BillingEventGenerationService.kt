package com.example.kotlin

import com.example.kotlin.security.Base64Encoder
import com.example.kotlin.security.domain.BillingEvent
import com.example.kotlin.security.domain.CompanyProduct
import com.example.kotlin.security.DefaultBillingEventCanonicalizer
import com.example.kotlin.security.domain.Duration
import com.example.kotlin.security.hash.Sha256HashGenerator
import org.springframework.stereotype.Service
import java.util.UUID
import kotlin.random.Random

@Service
class BillingEventGenerationService {
    fun generateMonthlyBillingEvents(
        duration: Duration,
        companyProduct: CompanyProduct
    ): List<GeneratedBillingEvent> {
        val result = mutableListOf<GeneratedBillingEvent>()
        val shaGenerator = Sha256HashGenerator()
        val canonicalizer = DefaultBillingEventCanonicalizer()
        val encoder = Base64Encoder()

        var currentMonthStart = duration.startDate.withDayOfMonth(1)

        while (!currentMonthStart.isAfter(duration.endDate)) {
            val currentMonthEnd = currentMonthStart.withDayOfMonth(
                currentMonthStart.month.length(currentMonthStart.isLeapYear)
            )

            val billingEvent = BillingEvent(
                id = Random.nextInt(1, 10000),
                transactionId = UUID.randomUUID().toString(),
                companyProduct = companyProduct,
                duration = Duration(
                    startDate = currentMonthStart,
                    endDate = currentMonthEnd
                ),
                hashV2 = null
            )

            val canonical = canonicalizer.canonicalize(billingEvent)
            val hash = encoder.encode(shaGenerator.hash(canonical))

            result += GeneratedBillingEvent(
                event = billingEvent,
                canonical = canonical,
                hash = hash
            )

            currentMonthStart = currentMonthStart.plusMonths(1)
        }

        return result
    }
}

data class GeneratedBillingEvent(
    val event: BillingEvent,
    val canonical: String,
    val hash: String
)
