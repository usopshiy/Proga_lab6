package io;

/**
 * wrapper for commands for easier usage of user input
 */
public class CommandWrapper {
    private final String command;

    private final String arg;

    public CommandWrapper(String cmd, String arg){
        this.arg = arg;
        this.command = cmd;
    }

    public CommandWrapper(String cmd){
        this.command = cmd;
        arg = null;
    }

    public String getCommand() {
        return command;
    }

    public String getArg() {
        return arg;
    }
}
