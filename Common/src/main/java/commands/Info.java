package commands;

import collection.RouteCollectionHandler;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import static utils.ConsoleColors.*;
public class Info implements Command{
    private final RouteCollectionHandler collectionHandler;

    public Info(RouteCollectionHandler collectionHandler) {
        this.collectionHandler = collectionHandler;
    }

    @Override
    public void execute(String arg) throws CommandException, InvalidDataException {
        collectionHandler.info();
    }

    @Override
    public void outDescription() {
        System.out.println(setColor(GREEN_BOLD_BRIGHT, "info ")  +
                "- " + setColor(BLUE_BRIGHT, "shows info about the collection"));
    }
}
