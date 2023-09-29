import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PasswordManagerUtils {

    public static Credentials showPassword(String alias, Configuration config, String masterPassword) throws IOException {
        Credentials cred = null;
        for (Configuration.StorageDescription rd: config.storages) {
            PasswordStorage rep = PasswordStorageFactory.getStorage(rd);
            cred = rep.getEntry(alias, masterPassword);
            if (cred != null) {
                return cred;
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
        for (Configuration.StorageDescription rd: config.storages) {
            PasswordStorage rep = PasswordStorageFactory.getStorage(rd);
            rep.addEntry(alias, cred, masterPassword);
        }
    }
    public static void removePassword(String alias, Configuration config, String masterPassword) throws IOException {
        for (Configuration.StorageDescription rd: config.storages) {
            PasswordStorage rep = PasswordStorageFactory.getStorage(rd);
            rep.removeEntry(alias, masterPassword);
        }
    }

}
