class HexEncoder: Encoder {
    override val type = EncoderType.HEX
    override fun encode(bytes: ByteArray): String {
        val sb = StringBuilder()
        for (b in bytes) {
            val hex = Integer.toHexString(0xff and b.toInt())
            if(hex.length == 1) sb.append('0')
            sb.append(hex)
        }
        return sb.toString()
    }
}