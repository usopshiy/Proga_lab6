package commands;

import collection.RouteCollectionHandler;
import connection.AnswerMsg;
import connection.RequestMsg;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import static utils.ConsoleColors.*;
import java.util.UUID;

public class RemoveByID implements Command{
    private final RouteCollectionHandler collectionHandler;

    public RemoveByID() {
        collectionHandler = null;
    }

    public RemoveByID(RouteCollectionHandler handler){
        this.collectionHandler = handler;
    }

    @Override
    public AnswerMsg execute(Object arg) throws CommandException, InvalidDataException {
        if (!collectionHandler.validateID((String) arg)){
            throw new InvalidDataException("Invalid id");
        }
        UUID id = UUID.fromString((String) arg); //Exception-save cause checks in validate
        if (collectionHandler.getCollection().isEmpty()) {
            throw new CommandException("collection is empty!");
        }
        if(collectionHandler.checkID(id)){
            throw new CommandException("id is not present in collection!");
        }
        return new AnswerMsg(collectionHandler.removeByID(id));
    }

    @Override
    public void outDescription() {
        System.out.println(setColor(GREEN_BOLD_BRIGHT, "remove_by_id ") + setColor(PURPLE_BRIGHT, "id ") +
                "- " + setColor(BLUE_BRIGHT, "removes route with given id from collection"));
    }

    @Override
    public RequestMsg makeRequest(String arg) throws InvalidDataException {
        return new RequestMsg("remove_by_id", arg, null);
    }
}
