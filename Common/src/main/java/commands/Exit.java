package commands;

import connection.AnswerMsg;
import connection.RequestMsg;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import io.UserInputHandler;
import static utils.ConsoleColors.*;
public class Exit implements Command{
    private final UserInputHandler userInputHandler;

    public Exit(UserInputHandler userInputHandler){
        this.userInputHandler = userInputHandler;
    }

    @Override
    public AnswerMsg execute(Object arg) throws CommandException, InvalidDataException {
        return new AnswerMsg(userInputHandler.exit());
    }

    @Override
    public void outDescription() {
        System.out.println(setColor(GREEN_BOLD_BRIGHT, "exit ") +
                "- " + setColor(BLUE_BRIGHT, "exits program"));
    }

    @Override
    public RequestMsg makeRequest(String arg) throws InvalidDataException {
        return new RequestMsg("close", arg, null);
    }
}
