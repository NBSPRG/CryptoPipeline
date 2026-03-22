package com.example.kotlin.security

class InsertionOrderDimensionNormalizer: DimensionNormalizer {
   // kotlin map preserves the order in which the input is given
   override fun canonicalize(input: Map<String, String>): String {
        val sb = StringBuilder()
        sb.append("{")

        for((key_, value_) in input) {
            sb.append("$key_:$value_|")
        }
        if(sb.last() == '|') {
            sb.deleteCharAt(sb.lastIndex)
        }

        sb.append("}")
        return sb.toString()
   } 
}
