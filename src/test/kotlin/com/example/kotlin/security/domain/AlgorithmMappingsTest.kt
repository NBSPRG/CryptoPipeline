package com.example.kotlin.security.domain

import kotlin.test.Test
import kotlin.test.assertEquals

class AlgorithmMappingsTest {

    @Test
    fun `EncrypterType AES maps to Algorithm AES`() {
        val algorithm = EncrypterType.AES.toAlgorithm()
        assertEquals(Algorithm.AES, algorithm)
    }

    @Test
    fun `EncrypterType DES maps to Algorithm DES`() {
        val algorithm = EncrypterType.DES.toAlgorithm()
        assertEquals(Algorithm.DES, algorithm)
    }

    @Test
    fun `EncrypterType RSA maps to Algorithm RSA`() {
        val algorithm = EncrypterType.RSA.toAlgorithm()
        assertEquals(Algorithm.RSA, algorithm)
    }

    @Test
    fun `EncrypterType DESede maps to Algorithm DESede`() {
        val algorithm = EncrypterType.DESede.toAlgorithm()
        assertEquals(Algorithm.DESede, algorithm)
    }

    @Test
    fun `EncrypterType ECDSA maps to Algorithm ECDSA`() {
        val algorithm = EncrypterType.ECDSA.toAlgorithm()
        assertEquals(Algorithm.ECDSA, algorithm)
    }

    @Test
    fun `Algorithm AES maps to EncrypterType AES`() {
        val encrypterType = Algorithm.AES.toEncrypterType()
        assertEquals(EncrypterType.AES, encrypterType)
    }

    @Test
    fun `Algorithm DES maps to EncrypterType DES`() {
        val encrypterType = Algorithm.DES.toEncrypterType()
        assertEquals(EncrypterType.DES, encrypterType)
    }

    @Test
    fun `Algorithm RSA maps to EncrypterType RSA`() {
        val encrypterType = Algorithm.RSA.toEncrypterType()
        assertEquals(EncrypterType.RSA, encrypterType)
    }

    @Test
    fun `Algorithm DESede maps to EncrypterType DESede`() {
        val encrypterType = Algorithm.DESede.toEncrypterType()
        assertEquals(EncrypterType.DESede, encrypterType)
    }

    @Test
    fun `Algorithm ECDSA maps to EncrypterType ECDSA`() {
        val encrypterType = Algorithm.ECDSA.toEncrypterType()
        assertEquals(EncrypterType.ECDSA, encrypterType)
    }

    @Test
    fun `bidirectional mapping from EncrypterType to Algorithm and back`() {
        val encrypterTypes = EncrypterType.values()
        
        encrypterTypes.forEach { encrypterType ->
            val algorithm = encrypterType.toAlgorithm()
            val mappedBackEncrypterType = algorithm.toEncrypterType()
            assertEquals(encrypterType, mappedBackEncrypterType, 
                "Bidirectional mapping failed for $encrypterType")
        }
    }

    @Test
    fun `bidirectional mapping from Algorithm to EncrypterType and back`() {
        val algorithms = Algorithm.values()
        
        algorithms.forEach { algorithm ->
            val encrypterType = algorithm.toEncrypterType()
            val mappedBackAlgorithm = encrypterType.toAlgorithm()
            assertEquals(algorithm, mappedBackAlgorithm, 
                "Bidirectional mapping failed for $algorithm")
        }
    }

    @Test
    fun `all EncrypterType values can be mapped to Algorithm`() {
        EncrypterType.values().forEach { encrypterType ->
            val algorithm = encrypterType.toAlgorithm()
            assertEquals(encrypterType.algorithm, algorithm.transformation)
        }
    }
}

