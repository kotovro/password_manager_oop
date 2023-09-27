public class CryptoFactory {
    public static CryptoProvider getCrypto(EncryptionType type) {
        switch (type) {
            case BASE_64: {
                return new CryptoBase64();
            }
        }
        return null;
    }
}
