public class CryptoFactory {
    public static CryptoProvider getCrypto(EncryptionType type) {
        switch (type) {
            case SIMPLE: {
                return new SimpleCrypto();
            }
        }
        return null;
    }
}
