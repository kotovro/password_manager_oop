import java.io.IOException;

public interface PasswordStorage {
    void addEntry(String alias, Credentials cred, String masterPassword) throws IOException;
    Credentials getEntry(String alias, String masterPassword) throws IOException;
}
