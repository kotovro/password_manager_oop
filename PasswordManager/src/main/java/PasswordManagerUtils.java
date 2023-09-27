import java.io.File;
import java.io.IOException;

public class PasswordManagerUtils {

    public static Credentials showPassword(String alias, Configuration config, String masterPassword) throws IOException {
        Credentials cred = null;
        for (Configuration.StorageDescription rd: config.repositories) {
            PasswordStorage rep = PasswordRepositoryFactory.getRepository(rd);
            cred = rep.getEntry(alias, masterPassword);
            if (cred != null) {
                return cred;
            }
        }
        return null;
    }

    public static void initRep(Configuration config, int encType, int parts) {
        Configuration.StorageDescription rd = new Configuration.StorageDescription();
        rd.type = rd.FILE;
        rd.encryptionType = EncryptionType.values()[encType];
        rd.parts = new String[parts];
        try {
            for (int i = 0; i < parts; i++) {
                rd.parts[i] = "file" + i + ".dpm";
                File file = new File(rd.parts[i]);
                file.createNewFile();
            }
            config.repositories.add(rd);
            config.writeRepToFile();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void addPassword(String alias, Credentials cred, Configuration config, String masterPassword) throws IOException {
        for (Configuration.StorageDescription rd: config.repositories) {
            PasswordStorage rep = PasswordRepositoryFactory.getRepository(rd);
            rep.addEntry(alias, cred, masterPassword);
        }
    }

}
