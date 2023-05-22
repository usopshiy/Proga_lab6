package io;

import collection.RouteCollectionHandler;
import commands.*;
import connection.AnswerMsg;
import connection.RequestMsg;
import connection.Server;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import file.FileHandler;

public class ServerUserInputHandler extends UserInputHandler {
    private Server server;
    private RouteCollectionHandler collectionHandler;
    private FileHandler fileHandler;

    public ServerUserInputHandler(Server serv, InputHandler handler){
        super(new ConsoleInputHandler());
        server = serv;
        collectionHandler = serv.getCollectionHandler();
        fileHandler = serv.getFileHandler();
        addCommand("help", new Help(super.getMap()));
        addCommand("info", new Info(this.collectionHandler));
        addCommand("show", new Show(this.collectionHandler));
        addCommand("add", new Add(this.collectionHandler, super.getCurrentHandler()));
        addCommand("update_by_id", new UpdateByID(this.collectionHandler, super.getCurrentHandler()));
        addCommand("remove_by_id", new RemoveByID(this.collectionHandler));
        addCommand("clear", new Clear(collectionHandler));
        addCommand("save", new Save(fileHandler, collectionHandler));
        addCommand("exit", new Exit(this));
        addCommand("add_if_max", new AddIfMax(super.getCurrentHandler(), this.collectionHandler));
        addCommand("add_if_min", new AddIfMin(super.getCurrentHandler(), this.collectionHandler));
        addCommand("remove_greater", new RemoveGreater(super.getCurrentHandler(), this.collectionHandler));
        addCommand("remove_any_by_distance", new RemoveAnyByDistance(this.collectionHandler));
        addCommand("group_counting_by_from", new GroupCountingByFrom(this.collectionHandler));
        addCommand("execute_script", new ExecuteScript(this));
        addCommand("print_ascending", new PrintAscending(this.collectionHandler));
    }

    public AnswerMsg runCommand(RequestMsg msg){
        AnswerMsg res = new AnswerMsg();
        try {
            String cmd = msg.getCommand();
            if(!getMap().containsKey(cmd)){
                throw new InvalidDataException("no such command exist");
            }
            if(msg.getRoute() != null){
                getMap().get(msg.getCommand()).execute(msg.getRoute());
            }
            else {
                getMap().get(msg.getCommand()).execute(msg.getArg());
            }
        } catch (CommandException | InvalidDataException e){
            ExceptionWrapper.outException(e.getMessage());
        }
        return res;
    }

    @Override
    public void consoleMode() {
        super.setCurrentHandler(new ConsoleInputHandler());
        super.setRunning(true);
        while (super.isRunning()) {
            System.out.print("enter command (help to get command list): ");
            CommandWrapper commandMsg = super.getCurrentHandler().readCommand();
            RequestMsg cmd = new RequestMsg(commandMsg.getCommand(), commandMsg.getArg(), null);
            AnswerMsg answerMsg = runCommand(cmd);
        }
    }

    public void exit(){
        super.setRunning(false);
        server.close();
    }
    @Override
    public void scriptMode(String path) {

    }
}
