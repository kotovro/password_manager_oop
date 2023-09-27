import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class PasswordUtils {

    public static void showPassword(String alias) {

    }

    public static void initRep(Configuration config) {
        Configuration.RepDescription rd = new Configuration.RepDescription();
        System.out.println("Choose encryption type: ");
        int i = 0;
        for (EncryptionType elem: EncryptionType.values()) {
            System.out.println(i + ". " + elem);
            i++;
        }
        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt();
        rd.encryptionType = EncryptionType.values()[option];
        System.out.println("Choose how many parts: ");
        option = sc.nextInt();
        String[] parts = new String[option];
        rd.parts = parts;
        try {


        for (i = 0; i < option; i++) {
            rd.parts[i] = "file" + i + ".dpm";
            File file = new File(rd.parts[i]);
            file.createNewFile();
        }
        config.repositories.add(rd);
        config.writeRepToFile();
        } catch (Exception ex) {
            throw new Exception;
        }
    }
}
