package connection;
import data.Route;

import java.io.Serializable;
public class RequestMsg implements Request {
    private String commandName;
    private String commandArg;
    private Serializable commandObjectArg;

    public RequestMsg(String comName, String comArg, Serializable comOA){
        commandName = comName;
        commandArg = comArg;
        commandObjectArg = comOA;
    }

    @Override
    public String getStringArg() {
        return commandArg;
    }

    public Route getRoute(){
        return (Route) commandObjectArg;
    }

    @Override
    public String getCommandName() {
        return commandName;
    }

    @Override
    public String toString() {
        return commandName + "\n" +
                commandArg + "\n" +
                commandObjectArg;
    }
}
