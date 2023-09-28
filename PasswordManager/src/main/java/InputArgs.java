import java.util.Arrays;

public class InputArgs {

    private String alias;

    public String getAlias() {
        return alias;
    }

    public class Commands {
        public static final String GET = "get";
        public static final String ADD = "add";
        public static final String LIST = "list";
        public static final String UPDATE = "update";
        public static final String ADD_STORAGE = "add-storage";
        public static final String REMOVE = "remove";
        public static final String[] validCommands = {GET, ADD, LIST, UPDATE, ADD_STORAGE, REMOVE};
    }

    private final String command;
    public String getCommand() {
        return command;
    }
    public InputArgs(String[] args) {
        if (args.length > 0 && Arrays.asList(Commands.validCommands).contains(args[0].toLowerCase())) {
            command = args[0].toLowerCase();
        } else {
            command = null;
            return;
        }
        if (args.length > 1 && (command.equals(Commands.GET)
                || command.equals(Commands.UPDATE)
                || command.equals(Commands.ADD)
        || command.equals(Commands.REMOVE))) {
            alias = args[1].toLowerCase();
        } else {
            alias = null;
        }

    }
}
