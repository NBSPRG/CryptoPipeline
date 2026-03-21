class CanonicalDimensionNormalizer: DimensionNormalizer {
    override fun canonicalize(input: Map<String, String>): String {
        val sb = StringBuilder()
        sb.append("{")

        val sortedKeys = input.keys
                        .map { it.trim().uppercase() }
                        .sorted()
        
        for(key in sortedKeys) {
            val originalKey = input.keys.first { it.trim().uppercase() == key }
            val value_ = input[originalKey]
                         ?.trim()
                         ?.uppercase()
                         ?: "NULL"

            sb.append("$key:$value_|")
        }

        if(sb.last() == '|') {
            sb.deleteCharAt(sb.lastIndex)
        }

        sb.append("}")
        return sb.toString()
    }
}
