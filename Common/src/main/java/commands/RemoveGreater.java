package commands;

import collection.RouteCollectionHandler;
import data.Route;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import io.InputHandler;
import static utils.ConsoleColors.*;
public class RemoveGreater implements Command {
    private final InputHandler inputHandler;
    private final RouteCollectionHandler collectionHandler;

    public RemoveGreater(InputHandler inputHandler, RouteCollectionHandler collectionHandler) {
        this.inputHandler = inputHandler;
        this.collectionHandler = collectionHandler;
    }
    @Override
    public void execute(String arg) throws CommandException, InvalidDataException {
        Route route = inputHandler.readRoute();
        collectionHandler.getCollection().removeIf(routech -> routech.compareTo(route) > 0);
    }

    @Override
    public void outDescription() {
        System.out.println(setColor(GREEN_BOLD_BRIGHT, "remove_greater  ") + setColor(PURPLE_BRIGHT, "{element} ") + "- " +
                setColor(BLUE_BRIGHT, "removes all elements in collection that greater then one that was given"));
    }
}
