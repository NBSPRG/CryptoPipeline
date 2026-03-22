package com.example.kotlin.security.services

import com.example.kotlin.security.CryptoRequest
import com.example.kotlin.security.EncryptResponse
import com.example.kotlin.security.HashResponse
import com.example.kotlin.security.SignResponse

interface CryptoService {
    fun <T> hash(request: CryptoRequest<T>): HashResponse
    fun <T> sign(request: CryptoRequest<T>): SignResponse
    fun <T> encrypt(request: CryptoRequest<T>): EncryptResponse

}