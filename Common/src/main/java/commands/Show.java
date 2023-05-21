package commands;

import collection.RouteCollectionHandler;
import data.Route;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import static utils.ConsoleColors.*;
public class Show implements Command{
    private final RouteCollectionHandler collectionHandler;

    public Show(RouteCollectionHandler collectionHandler) {
        this.collectionHandler = collectionHandler;
    }

    @Override
    public void execute(String arg) throws CommandException, InvalidDataException {
        if(collectionHandler.getCollection().isEmpty()){
            throw new CommandException("Collection is empty! nothing to show yet");
        }
        for(Route route : collectionHandler.getCollection()){
            System.out.println(route);
        }
    }

    @Override
    public void outDescription() {
        System.out.println(setColor(GREEN_BOLD_BRIGHT, "show ")  +
                "- " + setColor(BLUE_BRIGHT, "shows all elements of the collection"));
    }
}
