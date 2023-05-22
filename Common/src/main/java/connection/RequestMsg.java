package connection;
import data.Route;

import java.io.Serializable;
public class RequestMsg implements Serializable {
    private String commandName;
    private String commandArg;
    private Serializable commandObjectArg;

    public RequestMsg(String comName, String comArg, Serializable comOA){
        commandName = comName;
        commandArg = comArg;
        commandObjectArg = comOA;
    }

    public String getCommand(){
        return commandName;
    }

    public String getArg(){
        return commandArg;
    }

    public Route getRoute(){
        return (Route) commandObjectArg;
    }
}
