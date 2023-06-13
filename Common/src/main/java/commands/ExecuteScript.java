package commands;

import connection.AnswerMsg;
import connection.RequestMsg;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import io.UserInputHandler;
import static utils.ConsoleColors.*;
public class ExecuteScript implements Command{

    private final UserInputHandler userInputHandler;

    public ExecuteScript(UserInputHandler userInputHandler) {
        this.userInputHandler = userInputHandler;
    }

    @Override
    public AnswerMsg execute(Object arg) throws CommandException, InvalidDataException {
        if (arg.equals("")){ throw new InvalidDataException("missing Argument");}
        userInputHandler.executeScript((String) arg);
        return new AnswerMsg();
    }

    @Override
    public void outDescription() {
        System.out.println(setColor(GREEN_BOLD_BRIGHT, "execute_script ") + setColor(PURPLE_BRIGHT, "file ") +
                "- " + setColor(BLUE_BRIGHT, "execute script from file"));
    }

    @Override
    public RequestMsg makeRequest(String arg) throws InvalidDataException {
        return new RequestMsg("execute_script", arg, null);
    }
}
