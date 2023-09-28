import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void printHelp(String name) {
        System.out.printf("Usage: %s [options] [parameters]%n", name);
        }


    public static void main(String[] args) {
        System.out.println("Enter master password:");
        Scanner scanner = new Scanner(System.in);
        String masterPassword = scanner.nextLine();
        Configuration config = new Configuration("PassMan");
        InputArgs ia = new InputArgs(args);
        if (ia.getCommand() == null) {
            System.err.println("Insufficient arguments.");
            printHelp("PassMan");
            return;
        }
        if (!ia.getCommand().equals(InputArgs.Commands.LIST) && !ia.getCommand().equals(InputArgs.Commands.ADD_STORAGE) && ia.getAlias() == null) {
            System.err.println("Insufficient arguments.");
            printHelp("PassMan");
            return;
        }
        if (ia.getCommand().equals(InputArgs.Commands.GET)) {
            // get and print password
            try {
                Credentials cred = PasswordManagerUtils.showPassword(ia.getAlias(), config, masterPassword);
                System.out.println("Login: " + cred.getLogin() + "\nPassword: " + cred.getPassword());
            } catch (java.io.IOException ex) {
                System.out.println("Error getting password");
            }
        } else if (ia.getCommand().equals(InputArgs.Commands.ADD)) {
            // store new password
            System.out.println("Enter login:");
            String login = scanner.nextLine();
            System.out.println("Enter password:");
            String password = scanner.nextLine();
            Credentials cred = new Credentials(login, password);
            try {
                PasswordManagerUtils.addPassword(ia.getAlias(), cred, config, masterPassword);
            } catch (java.io.IOException ex) {
                System.out.println("Error adding entry");
            }
        } else if (ia.getCommand().equals(InputArgs.Commands.LIST)) {
            // show all aliases
        } else if (ia.getCommand().equals(InputArgs.Commands.UPDATE)) {
            // update alias
        } else if (ia.getCommand().equals(InputArgs.Commands.ADD_STORAGE)) {
            System.out.println("Choose encryption type: ");
            int i = 0;
            for (EncryptionType elem: EncryptionType.values()) {
                System.out.println(i + ". " + elem);
                i++;
            }
            Scanner sc = new Scanner(System.in);
            int encType = sc.nextInt();

            System.out.println("Choose how many parts: ");
            int parts = sc.nextInt();
            String type = Configuration.StorageDescription.FILE;
            try {
                PasswordManagerUtils.addStorage(config, encType, parts, type);
            } catch (IOException e) {
                System.out.println("Error creating storage:\n" + e.getMessage());
            }
        } else if (ia.getCommand().equals(InputArgs.Commands.REMOVE)) {

        }

    }
}
