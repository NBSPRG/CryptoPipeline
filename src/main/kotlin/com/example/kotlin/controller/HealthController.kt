package com.example.kotlin.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/api")
class HealthController {
    
    @GetMapping("/health")
    fun health(): HealthResponse {
        return HealthResponse(
            status = "UP",
            timestamp = LocalDateTime.now(),
            message = "CryptoPipeline Backend is running"
        )
    }
}

data class HealthResponse(
    val status: String,
    val timestamp: LocalDateTime,
    val message: String
)
