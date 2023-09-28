import java.io.*;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;


public class FilePasswordStorage implements PasswordStorage {

    private String[] parts = null;
    private CryptoProvider crypto = null;
    public FilePasswordStorage(Configuration.StorageDescription rd) {
        this.crypto = CryptoFactory.getCrypto(rd.encryptionType);
        this.parts = rd.parts;
    }

    public static void createFileStorage(Configuration.StorageDescription sd) {
        try {
            for (int i = 0; i < sd.parts.length; i++) {
                sd.parts[i] = UUID.randomUUID() + ".dpm";
                File file = new File(sd.parts[i]);
                file.createNewFile();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void addEntry(String alias, Credentials cred, String masterPassword) throws IOException {
        for(String part: parts) {
            File file = new File(part);
            Scanner sc = new Scanner(new FileReader(file));
            while (sc.hasNextLine()) {
                String line = sc.nextLine().split("\\s+")[0];
                if (line.equals(alias)) {
                    throw new RuntimeException("Alias already exists");
                }
            }
            sc.close();
        }
        Random random = new Random();
        String encrypted = crypto.encrypt(cred, masterPassword);
        int curPos = 0;
        Writer output;
        for (int i = 0; i < parts.length - 1; i++) {
            int length = random.nextInt(0, encrypted.length() - curPos);
            if (length > 0) {
                output = new BufferedWriter(new FileWriter(parts[i], true));
                output.append(alias + " " + encrypted.substring(curPos, curPos + length));
                output.close();
            }
            curPos += length;
            if (curPos == encrypted.length()) {
                return;
            }
        }
        if (encrypted.length() > curPos) {
            output = new BufferedWriter(new FileWriter(parts[parts.length - 1], true));
            output.append(alias + " " + encrypted.substring(curPos));
            output.close();
        }
    }

    @Override
    public Credentials getEntry(String alias, String masterPassword) throws IOException {
        String encrypted = "";
        for(String part: parts) {
            File file = new File(part);
            Scanner sc = new Scanner(new FileReader(file));
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.split("\\s+")[0].equals(alias)) {
                    encrypted += line.split("\\s+")[1];
                }
            }
            sc.close();
        }
        Credentials res = null;
        if (encrypted.length() != 0) {
            res = crypto.decrypt(encrypted, masterPassword);
        }
        return res;
    }

    @Override
    public void removeEntry(String alias, String masterPassword) throws IOException {

    }
}
