class CanonicalDimensionNormalizer: DimensionNormalizer {
    override fun canonicalize(input: Map<String, String>): String {
        val sb = StringBuilder()
        sb.append("{")

        val normalizedKeys = input.keys.groupBy { it.trim().uppercase() }
        val collisions = normalizedKeys.filterValues { it.size > 1 }
        require(collisions.isEmpty()) {
            "Dimension key collision after normalization: ${collisions.keys.sorted().joinToString(", ")}"
        }

        val sortedKeys = normalizedKeys.keys.sorted()

        for(key in sortedKeys) {
            val originalKey = normalizedKeys.getValue(key).single()
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
