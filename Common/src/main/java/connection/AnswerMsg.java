package connection;

import java.io.IOException;
import java.io.Serializable;

public class AnswerMsg implements Serializable {
    private static final long serialVersionUID = 420;
    private String msg;

    public AnswerMsg(){
        msg = "";
    }

    public void setMsg(Object o){
        msg = o.toString();
    }

    public String getMsg(){
        return msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}
