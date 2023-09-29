import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;


public class AESCrypto implements CryptoProvider {


    @Override
    public String encrypt(Credentials cred, String masterPassword) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String str = String.format("%s%n", mapper.writeValueAsString(cred));
        String salt = KeyGenerators.string().generateKey();
        TextEncryptor encryptor = Encryptors.text(masterPassword, salt);

        return salt + ":" + encryptor.encrypt(str);
    }

    @Override
    public Credentials decrypt(String cryptoString, String masterPassword) throws JsonProcessingException {
        String[] cryptoParts = cryptoString.split(":");
        TextEncryptor encryptor = Encryptors.text(masterPassword, cryptoParts[0]);
        String decoded = encryptor.decrypt(cryptoParts[1]);
        ObjectMapper mapper = new ObjectMapper();

        return  mapper.readValue(decoded, Credentials.class);
    }

}
