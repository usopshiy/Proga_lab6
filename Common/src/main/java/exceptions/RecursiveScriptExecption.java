package exceptions;

public class RecursiveScriptExecption extends CommandException {
    public RecursiveScriptExecption() {
        super("Script goes recursive!");
    }
}
