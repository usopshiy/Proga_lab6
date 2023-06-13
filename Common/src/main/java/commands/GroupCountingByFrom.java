package commands;

import collection.RouteCollectionHandler;
import connection.AnswerMsg;
import connection.RequestMsg;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import static utils.ConsoleColors.*;
public class GroupCountingByFrom implements Command{
    private final RouteCollectionHandler collectionHandler;

    public GroupCountingByFrom() {
        collectionHandler = null;
    }

    public GroupCountingByFrom(RouteCollectionHandler collectionHandler) {
        this.collectionHandler = collectionHandler;
    }

    @Override
    public AnswerMsg execute(Object arg) throws CommandException, InvalidDataException {
        if (collectionHandler.getCollection().isEmpty()) {
            throw new CommandException("collection is empty!");
        }
        return new AnswerMsg(collectionHandler.groupCountingByFrom());
    }

    @Override
    public void outDescription() {
        System.out.println(setColor(GREEN_BOLD_BRIGHT, "group_counting_by_from ")  +
                        "- " + setColor(BLUE_BRIGHT, "group_counting_by_from - groups elements in collection by \"from\" field and shows it"));
    }

    @Override
    public RequestMsg makeRequest(String arg) throws InvalidDataException {
        return new RequestMsg("group_counting_by_from", arg, null);
    }
}
