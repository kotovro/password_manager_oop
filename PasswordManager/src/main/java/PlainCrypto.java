import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;

public class PlainCrypto implements  CryptoProvider {
    @Override
    public String encrypt(Credentials cred, String masterPassword) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String str = String.format("%s%n", mapper.writeValueAsString(cred));

        return str;
    }

    @Override
    public Credentials decrypt(String cryptoString, String masterPassword) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        return  mapper.readValue(cryptoString, Credentials.class);
    }
}
