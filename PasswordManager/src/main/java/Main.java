import java.io.IOException;
import java.util.HashSet;
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
            System.out.println("Available commands:\n");
            int i = 0;
            for (String command : InputArgs.Commands.allCommands()) {
                System.out.println(i + ". " + command);
                i++;
            }
            System.out.println("Choose one of them:");
            ia.command = InputArgs.Commands.values()[scanner.nextInt()];
            scanner.nextLine();
        }
        if (!ia.getCommand().equals(InputArgs.Commands.LIST)
                && !ia.getCommand().equals(InputArgs.Commands.CHANGE_PASSWORD)
                && !ia.getCommand().equals(InputArgs.Commands.ADD_STORAGE)
                && ia.getAlias() == null) {
            System.out.println("Enter alias:");
            ia.alias = scanner.nextLine();
        }
        switch (ia.getCommand()) {
            case GET: {
                // get and print password
                try {
                    Credentials cred = PasswordManagerUtils.showPassword(ia.getAlias(), config, masterPassword);
                    if (cred != null) {
                        System.out.println("Login: " + cred.getLogin() + "\nPassword: " + cred.getPassword());
                    } else {
                        System.out.println("Error getting password");
                    }
                } catch (java.io.IOException ex) {
                    System.out.println("Error getting password");
                }
                break;
            }
            case ADD: {
                // store new password
                if (ia.login == null) {
                    System.out.println("Enter login:");
                    ia.login = scanner.nextLine();
                }
                if (ia.password == null) {
                    System.out.println("Enter password:");
                    ia.password = scanner.nextLine();
                }
                Credentials cred = new Credentials(ia.login, ia.password);
                try {
                    PasswordManagerUtils.addPassword(ia.getAlias(), cred, config, masterPassword);
                } catch (java.io.IOException ex) {
                    System.out.println("Error adding entry");
                }
                break;
            }
            case LIST: {
                // show all aliases
                try {
                    HashSet<String> passwords = PasswordManagerUtils.listPasswords(config, masterPassword);
                    for (String alias: passwords) {
                        System.out.println(alias);
                    }
                } catch (IOException e) {
                    System.out.println("Error retrieving passwords list:\n" + e.getMessage());
                }
                break;
            }
            case UPDATE: {
                // update alias
                if (ia.login == null) {
                    System.out.println("Enter login:");
                    ia.login = scanner.nextLine();
                }
                if (ia.password == null) {
                    System.out.println("Enter password:");
                    ia.password = scanner.nextLine();
                }
                Credentials cred = new Credentials(ia.login, ia.password);
                try {
                    PasswordManagerUtils.updatePassword(ia.getAlias(), cred, config, masterPassword);
                } catch (java.io.IOException ex) {
                    System.out.println("Error updating entry:\n" + ex.getMessage());
                }
                break;
            }
            case ADD_STORAGE: {
                System.out.println("Choose encryption type: ");
                int i = 0;
                for (EncryptionType elem : EncryptionType.values()) {
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
                break;
            }
            case REMOVE: {
                try {
                    PasswordManagerUtils.removePassword(ia.getAlias(), config, masterPassword);
                } catch (IOException e) {
                    System.out.println("Error deleting entry:\n" + e.getMessage());
                }
                break;
            }
            case CHANGE_PASSWORD: {
                System.out.println("Enter new master password:");
                String newPassword = scanner.nextLine();
                PasswordManagerUtils.changePassword(config, masterPassword, newPassword);
            }
        }

    }
}
