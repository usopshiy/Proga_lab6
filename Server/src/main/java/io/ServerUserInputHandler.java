package io;

import collection.RouteCollectionHandler;
import commands.*;
import connection.AnswerMsg;
import connection.RequestMsg;
import connection.Server;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import exceptions.RecursiveScriptExecption;
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
            String cmd = msg.getCommandName();
            if(!getMap().containsKey(cmd)){
                throw new InvalidDataException("no such command exist");
            }
            if(msg.getRoute() != null){
                res = getMap().get(msg.getCommandName()).execute(msg.getRoute());
            }
            else {
                res = getMap().get(msg.getCommandName()).execute(msg.getStringArg());
            }
        } catch (CommandException | InvalidDataException e){
            ExceptionWrapper.outException(e.getMessage());
        }
        System.out.println(res.getMsg());
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

    public String exit(){
        super.setRunning(false);
        server.close();
        return "Exiting programm";
    }
    @Override
    public void scriptMode(String path) {
        super.setPath(path);
        super.setCurrentHandler(new FileInputHandler(path));
        super.setRunning(true);
        while(super.isRunning() && super.getCurrentHandler().getScanner().hasNextLine()){
            CommandWrapper commandMsg = super.getCurrentHandler().readCommand();
            RequestMsg msg = new RequestMsg(commandMsg.getCommand(), commandMsg.getArg(), null);
            AnswerMsg answerMsg = runCommand(msg);
        }
    }

    @Override
    public String executeScript(String arg) throws RecursiveScriptExecption {
        if (getScriptStack().contains(arg))throw new RecursiveScriptExecption();
        getScriptStack().add(arg);
        ServerUserInputHandler process = new ServerUserInputHandler(server, new FileInputHandler(arg));
        process.scriptMode(arg);
        getScriptStack().pop();
        return "script successfully executed";
    }
}
