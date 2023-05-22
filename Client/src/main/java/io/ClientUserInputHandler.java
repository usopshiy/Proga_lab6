package io;

import commands.*;
import connection.AnswerMsg;
import connection.Client;
import connection.RequestMsg;
import exceptions.CommandException;
import exceptions.ConnectionException;
import exceptions.InvalidDataException;
import java.util.ArrayList;

public class ClientUserInputHandler extends UserInputHandler {
    private Client client;
    private ArrayList<String> clientExecutable;
    public ClientUserInputHandler(Client client){
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

    @Override
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
            }
        }
        catch (CommandException | InvalidDataException | ConnectionException e){
            System.out.println(e.getMessage());
        }
    }
}
