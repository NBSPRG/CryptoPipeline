package com.example.kotlin.security

class CryptoPipelineFactory(
    executors: List<PipelineExecutor>
) {
    private val registry: Map<String, PipelineExecutor> = 
        executors.associateBy { it.type }

    fun get(type: String): PipelineExecutor = 
        registry[type]
            ?: error("No executor for type: $type")
}