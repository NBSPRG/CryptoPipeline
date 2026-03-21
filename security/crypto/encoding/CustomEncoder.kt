import java.math.BigInteger

class CustomBaseEncoder(
    private val alphabet: String
): Encoder {
    override val type = EncoderType.CUSTOM
    override fun encode(bytes: ByteArray): String {
        var number = BigInteger(1, bytes)
        val base = alphabet.length
        val sb = StringBuilder()

        if(number == BigInteger.ZERO) return alphabet[0].toString()

        while(number > BigInteger.ZERO) {
            val divRem = number.divideAndRemainder(BigInteger.valueOf(base.toLong()))
            sb.append(alphabet[divRem[1].toInt()])
            number = divRem[0]
        }

        return sb.reverse().toString()
    }
}
