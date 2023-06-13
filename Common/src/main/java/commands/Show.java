package commands;

import collection.RouteCollectionHandler;
import connection.AnswerMsg;
import connection.RequestMsg;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import static utils.ConsoleColors.*;
public class Show implements Command{
    private final RouteCollectionHandler collectionHandler;

    public Show() {
        collectionHandler = null;
    }

    public Show(RouteCollectionHandler collectionHandler) {
        this.collectionHandler = collectionHandler;
    }

    @Override
    public AnswerMsg execute(Object arg) throws CommandException, InvalidDataException {
        if(collectionHandler.getCollection().isEmpty()){
            throw new CommandException("Collection is empty! nothing to show yet");
        }
        StringBuilder k = new StringBuilder();
        collectionHandler.getCollection().forEach(k::append);
        return new AnswerMsg(k.toString());
    }

    @Override
    public void outDescription() {
        System.out.println(setColor(GREEN_BOLD_BRIGHT, "show ")  +
                "- " + setColor(BLUE_BRIGHT, "shows all elements of the collection"));
    }

    @Override
    public RequestMsg makeRequest(String arg) throws InvalidDataException {
        return new RequestMsg("show", arg, null);
    }
}
