package io;

import commands.*;
import connection.AnswerMsg;
import connection.Client;
import connection.RequestMsg;
import exceptions.CommandException;
import exceptions.ConnectionException;
import exceptions.InvalidDataException;
import exceptions.RecursiveScriptExecption;

import java.util.ArrayList;

public class ClientUserInputHandler extends UserInputHandler {
    private Client client;
    private ArrayList<String> clientExecutable;
    public ClientUserInputHandler(Client client){
        super.setCurrentHandler(new ConsoleInputHandler());
        this.client = client;
        clientExecutable = new ArrayList<>();
        addCommand("help", new Help(super.getMap()));
        addCommand("info", new Info());
        addCommand("show", new Show());
        addCommand("add", new Add(super.getCurrentHandler()));
        addCommand("update_by_id", new UpdateByID(super.getCurrentHandler()));
        addCommand("remove_by_id", new RemoveByID());
        addCommand("clear", new Clear());
        addCommand("exit", new Exit(this));
        addCommand("add_if_max", new AddIfMax(super.getCurrentHandler()));
        addCommand("add_if_min", new AddIfMin(super.getCurrentHandler()));
        addCommand("remove_greater", new RemoveGreater(super.getCurrentHandler()));
        addCommand("remove_any_by_distance", new RemoveAnyByDistance());
        addCommand("group_counting_by_from", new GroupCountingByFrom());
        addCommand("execute_script", new ExecuteScript(this));
        addCommand("print_ascending", new PrintAscending());
        addClientExecutable("help");
        addClientExecutable("exit");
        addClientExecutable("execute_script");
    }

    public void addClientExecutable(String msg){
        clientExecutable.add(msg);
    }


    public void runCommand(String cmd, String arg) {
        try {
            if (hasCommand(cmd)) {
                if (clientExecutable.contains(cmd)) {
                    super.getMap().get(cmd).execute(arg);
                }
                else{
                    RequestMsg msg = super.getMap().get(cmd).makeRequest(arg);
                    client.send(msg);
                    AnswerMsg res = client.receive();
                    System.out.println(res);
                }
            }else System.out.println("no such command exist");
        }
        catch (CommandException | InvalidDataException | ConnectionException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String executeScript(String arg) throws RecursiveScriptExecption {
        if (getScriptStack().contains(arg))throw new RecursiveScriptExecption();
        getScriptStack().add(arg);
        ClientUserInputHandler process = new ClientUserInputHandler(client);
        process.scriptMode(arg);
        getScriptStack().pop();
        return "script successfully executed";
    }

    @Override
    public void consoleMode() {
        super.setCurrentHandler(new ConsoleInputHandler());
        super.setRunning(true);
        while (super.isRunning()) {
            System.out.print("enter command (help to get command list): ");
            CommandWrapper commandMsg = super.getCurrentHandler().readCommand();
            runCommand(commandMsg.getCommand(), commandMsg.getArg());
        }
    }

    @Override
    public void scriptMode(String path) {
        super.setPath(path);
        super.setCurrentHandler(new FileInputHandler(path));
        super.setRunning(true);
        while(super.isRunning() && super.getCurrentHandler().getScanner().hasNextLine()){
            CommandWrapper commandMsg = super.getCurrentHandler().readCommand();
            runCommand(commandMsg.getCommand(), commandMsg.getArg());
            }
        }
}
