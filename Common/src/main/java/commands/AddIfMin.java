package commands;

import collection.RouteCollectionHandler;
import data.Route;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import io.InputHandler;

import static utils.ConsoleColors.*;

public class AddIfMin  implements Command{
    private final InputHandler inputHandler;
    private final RouteCollectionHandler collectionHandler;

    public AddIfMin(InputHandler inputHandler, RouteCollectionHandler collectionHandler) {
        this.inputHandler = inputHandler;
        this.collectionHandler = collectionHandler;
    }

    @Override
    public void execute(String arg) throws CommandException, InvalidDataException {
        Route route = inputHandler.readRoute();
        boolean isMin = true;
        for(Route routech : collectionHandler.getCollection()){
            if (route.compareTo(routech) >= 0) {
                isMin = false;
                break;
            }
        }
        if(isMin){
            collectionHandler.add(route);
        }
    }

    @Override
    public void outDescription() {
        System.out.println(setColor(GREEN_BOLD_BRIGHT, "add_if_min ") + setColor(PURPLE_BRIGHT, "{element} ") +
                "- " + setColor(BLUE_BRIGHT, "adds new element in collection if it's the smallest one"));
    }
}
