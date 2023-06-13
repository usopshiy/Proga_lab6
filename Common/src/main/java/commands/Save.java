package commands;

import collection.RouteCollectionHandler;
import connection.AnswerMsg;
import connection.RequestMsg;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import file.FileHandler;
import static utils.ConsoleColors.*;

public class Save implements Command{
    private final FileHandler fileHandler;
    private final RouteCollectionHandler collectionHandler;

    public Save(FileHandler fileHandler, RouteCollectionHandler collectionHandler) {
        this.fileHandler = fileHandler;
        this.collectionHandler = collectionHandler;
    }

    @Override
    public AnswerMsg execute(Object arg) throws CommandException, InvalidDataException {
        if (!((arg == null) || arg.equals(""))) fileHandler.setPath((String) arg);
        if (collectionHandler.getCollection().isEmpty()) System.out.println("there is nothing to save yet");
        if (!fileHandler.write(collectionHandler.serializeCollection())) throw new CommandException("cannot save collection");
        else return new AnswerMsg("successfully saved collection");
    }

    @Override
    public void outDescription() {
        System.out.println(setColor(GREEN_BOLD_BRIGHT, "save ")  + setColor(PURPLE_BRIGHT, "file") +
                "- " + setColor(BLUE_BRIGHT, "saves collection into file"));
    }

    @Override
    public RequestMsg makeRequest(String arg) throws InvalidDataException {
        return new RequestMsg("save", arg, null);
    }
}
