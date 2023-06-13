package commands;

import connection.AnswerMsg;
import connection.RequestMsg;
import exceptions.CommandException;
import exceptions.InvalidDataException;

import java.util.HashMap;
import static utils.ConsoleColors.*;
public class Help implements Command{
    private final HashMap<String, Command> map;

    public Help(HashMap<String, Command> map) {
        this.map = map;
    }

    @Override
    public AnswerMsg execute(Object arg) throws CommandException, InvalidDataException {
        map.keySet()
                .forEach(s -> map.get(s).outDescription());
        return new AnswerMsg();
    }

    @Override
    public void outDescription() {
        System.out.println(setColor(GREEN_BOLD_BRIGHT, "help ")  +
                "- " + setColor(BLUE_BRIGHT, "shows available commands and their description"));
    }

    @Override
    public RequestMsg makeRequest(String arg) throws InvalidDataException {
        return new RequestMsg("help", arg, null);
    }
}
