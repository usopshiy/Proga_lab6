package io;

import commands.ExceptionWrapper;
import exceptions.InvalidDataException;

/**
 * wrapper class for user input requests
 * @param <T>
 */
public class Request<T> {
    private T respond;

    public Request(String msg, Askable<T> askable){
        while(true){
            try{
                System.out.print(msg + " ");
                respond = askable.ask();
                break;

            }catch (InvalidDataException e){
                ExceptionWrapper.outException(e.getMessage());
            }
        }
    }

    public T getRespond(){
        return respond;
    }
}
