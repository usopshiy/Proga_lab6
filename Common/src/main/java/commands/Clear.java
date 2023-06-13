package commands;

import collection.RouteCollectionHandler;
import connection.AnswerMsg;
import connection.RequestMsg;
import exceptions.CommandException;
import exceptions.InvalidDataException;

import static utils.ConsoleColors.*;
public class Clear implements Command {
    private final RouteCollectionHandler collectionHandler;

    public Clear() {
        collectionHandler = null;
    }

    public  Clear(RouteCollectionHandler collectionHandler){
        this.collectionHandler = collectionHandler;
    }
    @Override
    public AnswerMsg execute(Object arg) throws CommandException, InvalidDataException {
        return new AnswerMsg(collectionHandler.clear());
    }

    @Override
    public void outDescription(){
        System.out.println(setColor(GREEN_BOLD_BRIGHT, "clear ") +
                        "- " + setColor(BLUE_BRIGHT, "removes all routes from collection"));
    }

    @Override
    public RequestMsg makeRequest(String arg) throws InvalidDataException {
        return new RequestMsg("clear", arg, null);
    }
}
