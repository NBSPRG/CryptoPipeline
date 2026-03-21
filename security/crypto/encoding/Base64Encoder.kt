import java.util.Base64

class Base64Encoder: Encoder {
    override val type = EncoderType.BASE64
    
    override fun encode(bytes: ByteArray): String {
        return Base64.getEncoder().encodeToString(bytes)
    }
}
