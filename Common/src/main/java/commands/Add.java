package commands;

import collection.RouteCollectionHandler;
import connection.RequestMsg;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import io.InputHandler;
import static  utils.ConsoleColors.*;

public class Add implements  Command {
    private final RouteCollectionHandler collectionHandler;
    private final InputHandler inputHandler;

    public Add(InputHandler inputHandler){
        this.collectionHandler = null;
        this.inputHandler = inputHandler;
    }
    public Add(RouteCollectionHandler routeCollectionHandler, InputHandler inputHandler){
        this.collectionHandler = routeCollectionHandler;
        this.inputHandler = inputHandler;
    }
    @Override
    public void execute(String arg) throws CommandException, InvalidDataException {
        collectionHandler.add(inputHandler.readRoute());
    }

    @Override
    public void outDescription() {
        System.out.println(setColor(GREEN_BOLD_BRIGHT, "add ") + setColor(PURPLE_BRIGHT, "{element} ") + "- " +
                setColor(BLUE_BRIGHT, "adds new route in collection"));
    }

    @Override
    public RequestMsg makeRequest(String arg) throws InvalidDataException {
        return new RequestMsg("add", arg, inputHandler.readRoute());
    }
}