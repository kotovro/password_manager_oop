public class CryptoFactory {
    public static CryptoProvider getCrypto(EncryptionType type) {
        switch (type) {
            case SIMPLE: {
                return new SimpleCrypto();
            }
            case AES: {
                return new AESCrypto();
            }
            case PLAIN_TEXT:{
                return new PlainCrypto();
            }
        }
        return null;
    }
}
