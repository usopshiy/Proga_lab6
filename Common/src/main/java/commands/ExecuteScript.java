package commands;

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
    public void execute(String arg) throws CommandException, InvalidDataException {
        if (arg.equals("")){ throw new InvalidDataException("missing Argument");}
        userInputHandler.executeScript(arg);
    }

    @Override
    public void outDescription() {
        System.out.println(setColor(GREEN_BOLD_BRIGHT, "execute_script ") + setColor(PURPLE_BRIGHT, "file ") +
                "- " + setColor(BLUE_BRIGHT, "execute script from file"));
    }
}
