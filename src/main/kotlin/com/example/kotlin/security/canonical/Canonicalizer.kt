package com.example.kotlin.security

import com.example.kotlin.security.domain.CanonicalizerType

interface Canonicalizer<T> {
    val type: CanonicalizerType
    fun canonicalize(input: T): String 
}