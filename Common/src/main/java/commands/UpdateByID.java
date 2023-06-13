package commands;

import collection.RouteCollectionHandler;
import connection.AnswerMsg;
import connection.RequestMsg;
import data.Route;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import io.InputHandler;
import static utils.ConsoleColors.*;
import java.util.UUID;

public class UpdateByID implements Command{
    private  final RouteCollectionHandler collectionHandler;
    private final InputHandler inputHandler;

    public UpdateByID(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
        collectionHandler = null;
    }

    public UpdateByID(RouteCollectionHandler collectionHandler, InputHandler inputHandler) {
        this.collectionHandler = collectionHandler;
        this.inputHandler = inputHandler;
    }

    @Override
    public AnswerMsg execute(Object arg) throws CommandException, InvalidDataException {
        if (collectionHandler.validateID((String) arg)) {
            UUID id = UUID.fromString((String) arg);
            if (collectionHandler.getCollection().isEmpty()) {
                throw new CommandException("collection is empty!");
            }
            if (collectionHandler.checkID(id)) {
                throw new CommandException("id is not present in collection!");
            }
            Route route = inputHandler.readRoute();
            route.setId(id);
            return new AnswerMsg(collectionHandler.updateByID(id, route));
        }
        else throw new InvalidDataException("Invalid id");
    }

    @Override
    public void outDescription() {
        System.out.println(setColor(GREEN_BOLD_BRIGHT, "update_by_id ") + setColor(PURPLE_BRIGHT, "{element} ") + "- " +
                setColor(BLUE_BRIGHT, "updates route with given id"));
    }

    @Override
    public RequestMsg makeRequest(String arg) throws InvalidDataException {
        return new RequestMsg("update_by_id", arg, null);
    }
}
