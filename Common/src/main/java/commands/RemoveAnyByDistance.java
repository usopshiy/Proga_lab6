package commands;

import collection.RouteCollectionHandler;
import connection.AnswerMsg;
import connection.RequestMsg;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import static utils.ConsoleColors.*;
public class RemoveAnyByDistance implements Command{
    private final RouteCollectionHandler collectionHandler;

    public RemoveAnyByDistance() {
        collectionHandler = null;
    }

    public RemoveAnyByDistance(RouteCollectionHandler collectionHandler){
        this.collectionHandler = collectionHandler;
    }

    @Override
    public AnswerMsg execute(Object arg) throws CommandException, InvalidDataException {
        Double dist = (double) 0;
        if(arg != null) {
            try {
                dist = Double.parseDouble((String) arg);
            } catch (IllegalArgumentException e) {
                throw new InvalidDataException("invalid argument");
            }
            if(dist <= 1){throw new InvalidDataException("distance must be greater then 1");}
        }
        Double finalDist = dist;
        Long count = collectionHandler.getCollection().stream()
                        .filter(s -> s.getDistance().equals(finalDist))
                        .count();
        collectionHandler.getCollection().stream()
                .filter(s -> s.getDistance().equals(finalDist))
                .forEach(s -> collectionHandler.getCollection().remove(s));
        return new AnswerMsg("deleted " + count + " elements");
    }

    @Override
    public void outDescription() {
        System.out.println(setColor(GREEN_BOLD_BRIGHT, "remove_any_by_distance ") + setColor(PURPLE_BRIGHT, "distance ") +
                "- " + setColor(BLUE_BRIGHT, "removes first element with given distance"));
    }

    @Override
    public RequestMsg makeRequest(String arg) throws InvalidDataException {
        return new RequestMsg("remove_any_by_distance", arg, null);
    }
}
