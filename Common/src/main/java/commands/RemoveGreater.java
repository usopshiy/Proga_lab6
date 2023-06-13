package commands;

import collection.RouteCollectionHandler;
import connection.AnswerMsg;
import connection.RequestMsg;
import data.Route;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import io.InputHandler;
import static utils.ConsoleColors.*;
public class RemoveGreater implements Command {
    private final InputHandler inputHandler;
    private final RouteCollectionHandler collectionHandler;

    public RemoveGreater(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
        collectionHandler = null;
    }

    public RemoveGreater(InputHandler inputHandler, RouteCollectionHandler collectionHandler) {
        this.inputHandler = inputHandler;
        this.collectionHandler = collectionHandler;
    }
    @Override
    public AnswerMsg execute(Object arg) throws CommandException, InvalidDataException {
        Route route = null;
        if(arg != null && arg.getClass().equals(Route.class)) {
            route = (Route) arg;
        }
        else {
            route = inputHandler.readRoute();
        }
        Route finalRoute = route;
        collectionHandler.getCollection().removeIf(s -> s.compareTo(finalRoute) > 0);
        return new AnswerMsg("deleted all freater elements");
    }

    @Override
    public void outDescription() {
        System.out.println(setColor(GREEN_BOLD_BRIGHT, "remove_greater  ") + setColor(PURPLE_BRIGHT, "{element} ") + "- " +
                setColor(BLUE_BRIGHT, "removes all elements in collection that greater then one that was given"));
    }

    @Override
    public RequestMsg makeRequest(String arg) throws InvalidDataException {
        return new RequestMsg("remove_greater", arg, null);
    }
}
