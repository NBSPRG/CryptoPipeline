package com.example.kotlin.security.domain

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class AlgorithmTest {

    @Test
    fun `AES algorithm has correct properties`() {
        val algorithm = Algorithm.AES
        assertEquals(KeyType.SYMMETRIC, algorithm.keyType)
        assertEquals("AES", algorithm.transformation)
        assertEquals(256, algorithm.keySize)
    }

    @Test
    fun `DES algorithm has correct properties`() {
        val algorithm = Algorithm.DES
        assertEquals(KeyType.SYMMETRIC, algorithm.keyType)
        assertEquals("DES", algorithm.transformation)
        assertEquals(56, algorithm.keySize)
    }

    @Test
    fun `RSA algorithm has correct properties`() {
        val algorithm = Algorithm.RSA
        assertEquals(KeyType.ASYMMETRIC, algorithm.keyType)
        assertEquals("RSA", algorithm.transformation)
        assertEquals(2048, algorithm.keySize)
    }

    @Test
    fun `DESede algorithm has correct properties`() {
        val algorithm = Algorithm.DESede
        assertEquals(KeyType.SYMMETRIC, algorithm.keyType)
        assertEquals("DESede", algorithm.transformation)
        assertEquals(168, algorithm.keySize)
    }

    @Test
    fun `ECDSA algorithm has correct properties`() {
        val algorithm = Algorithm.ECDSA
        assertEquals(KeyType.ASYMMETRIC, algorithm.keyType)
        assertEquals("EC", algorithm.transformation)
        assertEquals(256, algorithm.keySize)
    }

    @Test
    fun `all algorithms are defined`() {
        val algorithms = Algorithm.values()
        assertEquals(5, algorithms.size)
        assertNotNull(algorithms.find { it == Algorithm.AES })
        assertNotNull(algorithms.find { it == Algorithm.DES })
        assertNotNull(algorithms.find { it == Algorithm.RSA })
        assertNotNull(algorithms.find { it == Algorithm.DESede })
        assertNotNull(algorithms.find { it == Algorithm.ECDSA })
    }

    @Test
    fun `symmetric algorithms have correct key type`() {
        val symmetricAlgorithms = listOf(Algorithm.AES, Algorithm.DES, Algorithm.DESede)
        symmetricAlgorithms.forEach { algorithm ->
            assertEquals(KeyType.SYMMETRIC, algorithm.keyType)
        }
    }

    @Test
    fun `asymmetric algorithms have correct key type`() {
        val asymmetricAlgorithms = listOf(Algorithm.RSA, Algorithm.ECDSA)
        asymmetricAlgorithms.forEach { algorithm ->
            assertEquals(KeyType.ASYMMETRIC, algorithm.keyType)
        }
    }

    @Test
    fun `RSA has larger key size than symmetric algorithms`() {
        val rsaKeySize = Algorithm.RSA.keySize
        val aesKeySize = Algorithm.AES.keySize
        val desKeySize = Algorithm.DES.keySize
        val desedeKeySize = Algorithm.DESede.keySize

        assert(rsaKeySize > aesKeySize)
        assert(rsaKeySize > desKeySize)
        assert(rsaKeySize > desedeKeySize)
    }

    @Test
    fun `algorithm names match their enum values`() {
        assertEquals("AES", Algorithm.AES.name)
        assertEquals("DES", Algorithm.DES.name)
        assertEquals("RSA", Algorithm.RSA.name)
        assertEquals("DESede", Algorithm.DESede.name)
        assertEquals("ECDSA", Algorithm.ECDSA.name)
    }
}

