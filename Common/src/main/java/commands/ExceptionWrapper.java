package commands;

import utils.DateConverter;

public class ExceptionWrapper {
    public static void outException(String message){
        System.out.println(message);
        System.err.println(DateConverter.dateToString(java.time.LocalDateTime.now()) + " " + message);
    }
}
