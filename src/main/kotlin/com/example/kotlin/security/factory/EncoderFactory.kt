package com.example.kotlin.security

class EncoderFactory(
    encoders: List<Encoder>
) {
    private val registry: Map<EncoderType, Encoder> = 
        encoders.associateBy { it.type }

    operator fun get(type: EncoderType): Encoder {
        return registry[type]
            ?: error("No encoder for type: $type")
    }
}
