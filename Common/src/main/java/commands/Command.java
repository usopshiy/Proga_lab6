package commands;
import connection.AnswerMsg;
import connection.RequestMsg;
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
     AnswerMsg execute(Object arg) throws CommandException, InvalidDataException;

     /**
      * method that gives description of command
      */
     void outDescription();

     RequestMsg makeRequest(String arg) throws InvalidDataException;
}
