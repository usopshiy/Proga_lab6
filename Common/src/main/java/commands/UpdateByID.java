package commands;

import collection.RouteCollectionHandler;
import data.Route;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import io.InputHandler;
import static utils.ConsoleColors.*;
import java.util.UUID;

public class UpdateByID implements Command{
    private  final RouteCollectionHandler collectionHandler;
    private final InputHandler inputHandler;

    public UpdateByID(RouteCollectionHandler collectionHandler, InputHandler inputHandler) {
        this.collectionHandler = collectionHandler;
        this.inputHandler = inputHandler;
    }

    @Override
    public void execute(String arg) throws CommandException, InvalidDataException {
        if (collectionHandler.validateID(arg)) {
            UUID id = UUID.fromString(arg);
            if (collectionHandler.getCollection().isEmpty()) {
                throw new CommandException("collection is empty!");
            }
            if (collectionHandler.checkID(id)) {
                throw new CommandException("id is not present in collection!");
            }
            Route route = inputHandler.readRoute();
            route.setId(id);
            collectionHandler.updateByID(id, route);
        }
        else throw new InvalidDataException("Invalid id");
    }

    @Override
    public void outDescription() {
        System.out.println(setColor(GREEN_BOLD_BRIGHT, "update_by_id ") + setColor(PURPLE_BRIGHT, "{element} ") + "- " +
                setColor(BLUE_BRIGHT, "updates route with given id"));
    }
}
