import java.io.IOException;

public interface PasswordStorage {
    void addEntry(String alias, Credentials cred, String masterPassword) throws IOException;
    Credentials getEntry(String alias, String masterPassword) throws IOException;
    void removeEntry(String alias, String masterPassword) throws IOException;
    default void updateEntry(String alias, Credentials cred, String masterPassword) throws IOException {
        removeEntry(alias, masterPassword);
        addEntry(alias, cred, masterPassword);
    }
}
