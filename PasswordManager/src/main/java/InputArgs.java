import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class InputArgs {

    public String login;
    public String password;
    public String alias;

    public String getAlias() {
        return alias;
    }

    public enum Commands {
        ADD("add"),
        GET("get"),
        UPDATE("update"),
        REMOVE("del"),
        LIST("list"),
        ADD_STORAGE("add-storage"),
        CHANGE_PASSWORD("change-password");


        private final String commandText;

        Commands(String command){
            this.commandText = command;
        }
        public String getCommandText() {
            return commandText;
        }
        private static final Map<String, Commands> lookup = new HashMap<>();
        private static final LinkedList<String> allCommands = new LinkedList<>();
        static {
            for (Commands command : Commands.values()) {
                lookup.put(command.getCommandText(), command);
                allCommands.add(command.getCommandText());
            }
        }
        public static LinkedList<String> allCommands() {
            return allCommands;
        }
        public static Commands command(String commandText) {
            return lookup.get(commandText);
        }
    }

    public  Commands command;
    public Commands getCommand() {
        return command;
    }
    public InputArgs(String[] args) {
        if (args.length > 0 && Commands.allCommands().contains(args[0].toLowerCase())) {
            command = Commands.command(args[0].toLowerCase());
        } else {
            command = null;
            return;
        }
        if (args.length > 1 && (command.equals(Commands.GET)
                || command.equals(Commands.UPDATE)
                || command.equals(Commands.ADD)
                || command.equals(Commands.REMOVE))) {
            alias = args[1].toLowerCase();
            if (args.length > 2) {
                login = args[2];
                if (args.length > 3) {
                    password = args[3];
                }
            }
        } else {
            alias = null;
        }

    }
}
