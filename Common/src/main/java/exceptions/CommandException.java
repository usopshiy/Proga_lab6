package exceptions;

public class CommandException extends RuntimeException{
    public CommandException(String msg){
        super(msg);
    }
}
