package com.example.kotlin.security

interface Canonicalizer<T> {
    val type: CanonicalizerType
    fun canonicalize(input: T): String 
}