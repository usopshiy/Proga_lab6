package commands;

import collection.RouteCollectionHandler;
import connection.RequestMsg;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import io.InputHandler;
import data.Route;

import static utils.ConsoleColors.*;

public class AddIfMax implements Command{
    private final InputHandler inputHandler;
    private final RouteCollectionHandler collectionHandler;

    public AddIfMax(InputHandler inputHandler){
        this.inputHandler = inputHandler;
        this.collectionHandler = null;
    }

    public AddIfMax(InputHandler inputHandler, RouteCollectionHandler collectionHandler) {
        this.inputHandler = inputHandler;
        this.collectionHandler = collectionHandler;
    }

    @Override
    public void execute(Object arg) throws CommandException, InvalidDataException {
        Route route = null;
        if(arg != null && arg.getClass().equals(Route.class)) {
            route = (Route) arg;
        }
        else {
            route = inputHandler.readRoute();
        }
        Route finalRoute = route;
        long count = collectionHandler.getCollection().stream()
                .filter(s -> finalRoute.compareTo(s) <= 0)
                .count();
        if (count == 0){
            collectionHandler.add(route);
        }
    }

    @Override
    public void outDescription() {
        System.out.println(setColor(GREEN_BOLD_BRIGHT, "add_if_max ") + setColor(PURPLE_BRIGHT, "{element} ") +
                "- " + setColor(BLUE_BRIGHT, "adds new element in collection if it's greater then all others" ));
    }

    @Override
    public RequestMsg makeRequest(String arg) throws InvalidDataException {
        return new RequestMsg("add_if_max", arg, inputHandler.readRoute());
    }
}