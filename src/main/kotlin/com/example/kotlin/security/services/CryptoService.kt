package com.example.kotlin.security

interface CryptoService {
    fun <T> hash(request: CryptoRequest<T>): HashResponse
    fun <T> sign(request: CryptoRequest<T>): SignResponse
    fun <T> encrypt(request: CryptoRequest<T>): EncryptResponse

}