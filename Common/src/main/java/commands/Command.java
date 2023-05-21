package commands;
import exceptions.*;

/**
 * Interface for commands
 */
public interface Command {
     /**
      * method for executing command
      * @param arg
      * @throws CommandException
      * @throws InvalidDataException
      */
     void execute(String arg) throws CommandException, InvalidDataException;

     /**
      * method that gives description of command
      */
     void outDescription();
}
