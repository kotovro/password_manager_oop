public class CryptoBase64 implements CryptoProvider {
    @Override
    public byte[] encrypt(byte[] credentials, byte[] masterPassword) {
        return xorWithKey(credentials, masterPassword);
    }
    @Override
    public byte[] decrypt(byte[] cryptoString, byte[] masterPassword) {
        return xorWithKey(cryptoString, masterPassword);
    }
    private byte[] xorWithKey(byte[] a, byte[] key) {
        byte[] out = new byte[a.length];
        for (int i = 0; i < a.length; i++) {
            out[i] = (byte) (a[i] ^ key[i%key.length]);
        }
        return out;
    }
}
