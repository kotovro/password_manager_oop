import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public interface PasswordStorage {
    void addEntry(String alias, Credentials cred, String masterPassword) throws IOException;
    Credentials getEntry(String alias, String masterPassword) throws IOException;
    void removeEntry(String alias, String masterPassword) throws IOException;
    boolean isEntryExists(String alias) throws IOException;
    HashSet<String> listEntries(String masterPassword) throws IOException;
    void changePassword(String oldPassword, String newPassword) throws IOException;
    default void updateEntry(String alias, Credentials cred, String masterPassword) throws IOException {
        removeEntry(alias, masterPassword);
        if (!isEntryExists(alias)) {
            addEntry(alias, cred, masterPassword);
        }
    }

}
