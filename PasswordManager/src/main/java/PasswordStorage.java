import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface PasswordRepository {
    public void addEntry(String alias, Credentials cred, String masterPassword) throws IOException;
}
