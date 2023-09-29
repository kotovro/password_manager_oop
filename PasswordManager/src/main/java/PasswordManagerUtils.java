import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.UUID;

public class PasswordManagerUtils {

    public static Credentials showPassword(String alias, Configuration config, String masterPassword) throws IOException {
        Credentials cred = null;
        for (Configuration.StorageDescription rd: config.storages) {
            PasswordStorage rep = PasswordStorageFactory.getStorage(rd);
            try {
                cred = rep.getEntry(alias, masterPassword);
                if (cred != null) {
                    return cred;
                }
            } catch (Exception ex) {

            }

        }
        return null;
    }

    public static void addStorage(Configuration config, int encType, int parts, String type) throws IOException {
        Configuration.StorageDescription sd = new Configuration.StorageDescription();
        sd.type = type;
        sd.encryptionType = EncryptionType.values()[encType];
        sd.parts = new String[parts];
        switch (type) {
            case Configuration.StorageDescription.FILE:
                FilePasswordStorage.createFileStorage(sd);
        }
        config.storages.add(sd);
        config.writeStoragesToFile();
    }

    public static void addPassword(String alias, Credentials cred, Configuration config, String masterPassword) throws IOException {
        if (isPasswordExists(alias, config)) {
            throw new RuntimeException("Alias already exists");
        }
        for (Configuration.StorageDescription rd: config.storages) {
            PasswordStorage rep = PasswordStorageFactory.getStorage(rd);
            rep.addEntry(alias, cred, masterPassword);
        }
    }
    public static void removePassword(String alias, Configuration config, String masterPassword) throws IOException {
        for (Configuration.StorageDescription rd: config.storages) {
            PasswordStorage rep = PasswordStorageFactory.getStorage(rd);
            try {
                rep.removeEntry(alias, masterPassword);
            } catch (Exception ex) {

            }
        }
    }

    public static void updatePassword(String alias, Credentials cred, Configuration config, String masterPassword) throws IOException {
        for (Configuration.StorageDescription rd: config.storages) {
            PasswordStorage rep = PasswordStorageFactory.getStorage(rd);
            try {
                rep.updateEntry(alias, cred, masterPassword);
            } catch (Exception ex) {

            }
        }
    }
    public static boolean isPasswordExists(String alias, Configuration config) throws IOException {
        for (Configuration.StorageDescription rd: config.storages) {
            PasswordStorage rep = PasswordStorageFactory.getStorage(rd);
            if (rep.isEntryExists(alias)) {
                return true;
            }
        }
        return false;
    }
    public static HashSet<String> listPasswords(Configuration config, String masterPassword) throws IOException {
        HashSet<String> result = new HashSet<>();
        for (Configuration.StorageDescription rd: config.storages) {
            PasswordStorage rep = PasswordStorageFactory.getStorage(rd);
            try {
                result.addAll(rep.listEntries(masterPassword));
            } catch (Exception ex) {

            }
        }
        return result;
    }

    public static void changePassword(Configuration config, String masterPassword, String newPassword) {
        for (Configuration.StorageDescription rd: config.storages) {
            PasswordStorage rep = PasswordStorageFactory.getStorage(rd);
            try {
                rep.changePassword(masterPassword, newPassword);
            } catch (Exception ex) {

            }
        }
    }
}
