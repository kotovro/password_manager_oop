import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public interface CryptoProvider {
    default String encrypt(Credentials cred, String masterPassword) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String str = String.format("%s%n", mapper.writeValueAsString(cred));
        return Base64.getEncoder().encodeToString(encrypt(str.getBytes(), masterPassword.getBytes()));
    }
    default Credentials decrypt(String cryptoString, String masterPassword) throws JsonProcessingException {
        byte[] crypto = Base64.getDecoder().decode(cryptoString);
        ObjectMapper mapper = new ObjectMapper();
        String decoded = new String(decrypt(crypto, masterPassword.getBytes()), StandardCharsets.UTF_8);
        Credentials cred = mapper.readValue(decoded, Credentials.class);
        return cred;
    }
    default byte[] encrypt(byte[] credentials, byte[] masterPassword) {
        return new byte[0];
    }
    default byte[] decrypt(byte[] cryptoString, byte[] masterPassword) {
        return new byte[0];
    };
}
