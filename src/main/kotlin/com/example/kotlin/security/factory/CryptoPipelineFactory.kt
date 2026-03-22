package com.example.kotlin.security.factory

import com.example.kotlin.security.pipeline.PipelineExecutor

class CryptoPipelineFactory(
    executors: List<PipelineExecutor>
) {
    private val registry: Map<String, PipelineExecutor> =
        executors.associateBy { it.type }

    fun get(type: String): PipelineExecutor =
        registry[type]
            ?: error("No executor for type: $type")
}