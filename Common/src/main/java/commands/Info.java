package commands;

import collection.RouteCollectionHandler;
import connection.AnswerMsg;
import connection.RequestMsg;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import static utils.ConsoleColors.*;
public class Info implements Command{
    private final RouteCollectionHandler collectionHandler;

    public Info(){
        this.collectionHandler = null;
    }
    public Info(RouteCollectionHandler collectionHandler) {
        this.collectionHandler = collectionHandler;
    }

    @Override
    public AnswerMsg execute(Object arg) throws CommandException, InvalidDataException {
        return new AnswerMsg(collectionHandler.info());
    }

    @Override
    public void outDescription() {
        System.out.println(setColor(GREEN_BOLD_BRIGHT, "info ")  +
                "- " + setColor(BLUE_BRIGHT, "shows info about the collection"));
    }

    @Override
    public RequestMsg makeRequest(String arg) throws InvalidDataException {
        return new RequestMsg("info", arg, null);
    }
}
