package com.example.kotlin.security.domain

enum class EncoderType(val encoder: String) {
    BASE64("BASE64"),
    HEX("HEX"),
    CUSTOM("CUSTOM")
}
