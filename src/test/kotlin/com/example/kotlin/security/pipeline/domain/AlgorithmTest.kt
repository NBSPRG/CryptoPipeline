package com.example.kotlin.security.pipeline.domain

import com.example.kotlin.security.domain.Algorithm
import com.example.kotlin.security.domain.KeyType
import kotlin.test.Test
import kotlin.test.assertEquals

class AlgorithmTest {
    @Test
    fun `AES algorithm should return correct value`() {
        val algorithm = Algorithm.AES
        assertEquals("AES", algorithm.name)
        assertEquals("AES", algorithm.transformation)
        assertEquals(KeyType.SYMMETRIC, algorithm.keyType)
        assertEquals(256, algorithm.keySize)

    }
}