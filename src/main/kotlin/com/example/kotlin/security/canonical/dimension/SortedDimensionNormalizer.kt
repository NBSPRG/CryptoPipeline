package com.example.kotlin.security

class SortedDimensionNormalizer: DimensionNormalizer {
    override fun canonicalize(input: Map<String, String>): String {
        val sb = StringBuilder()
        sb.append("{")

        val sortedKeys = input.keys.sorted()

        for(key in sortedKeys) {
            sb.append("$key:${input[key]}|")
        }

        if(sb.last() == '|') {
            sb.deleteCharAt(sb.lastIndex)
        }

        sb.append("}")
        return sb.toString()
    }
}
