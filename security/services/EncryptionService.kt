class EncryptionService(
    private val encryptionFactory: EncryptionFactory
) {
    fun encrypt(message: ByteArray, type: EncrypterType): ByteArray {
        val encrypter = encryptionFactory[type]
        return encrypter.encrypt(message)
    }
}
