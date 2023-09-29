public class CryptoFactory {
    public static CryptoProvider getCrypto(EncryptionType type) {
        switch (type) {
            case SIMPLE: {
                return new SimpleCrypto();
            }
            case AES: {
                return new AESCrypto();
            }
        }
        return null;
    }
}
