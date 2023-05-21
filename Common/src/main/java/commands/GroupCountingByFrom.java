package commands;

import collection.RouteCollectionHandler;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import static utils.ConsoleColors.*;
public class GroupCountingByFrom implements Command{
    private final RouteCollectionHandler collectionHandler;

    public GroupCountingByFrom(RouteCollectionHandler collectionHandler) {
        this.collectionHandler = collectionHandler;
    }

    @Override
    public void execute(String arg) throws CommandException, InvalidDataException {
        if (collectionHandler.getCollection().isEmpty()) {
            throw new CommandException("collection is empty!");
        }
        collectionHandler.groupCountingByFrom();
    }

    @Override
    public void outDescription() {
        System.out.println(setColor(GREEN_BOLD_BRIGHT, "group_counting_by_from ")  +
                        "- " + setColor(BLUE_BRIGHT, "group_counting_by_from - groups elements in collection by \"from\" field and shows it"));
    }
}
