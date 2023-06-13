package io;

import commands.*;
import connection.AnswerMsg;
import connection.RequestMsg;
import exceptions.*;

import java.util.HashMap;
import java.util.Stack;

/**
 * class that runs programm
 */
public abstract class UserInputHandler {
    private HashMap<String, Command> map;
    private InputHandler currentHandler;
    private String currentScriptFilePath = "";
    private boolean isRunning = false;

    private static final Stack<String> scriptStack = new Stack<>();

    public UserInputHandler(InputHandler handler){
        currentHandler = handler;
        map = new HashMap<>();
    }
    public UserInputHandler(){
        map = new HashMap<>();
    }

    /**
     * adds command to command list
     * @param key
     * @param command
     */
    public void addCommand(String key, Command command){
        map.put(key, command);
    }


    /**
     * exits program without saving collection
     */
    public String exit(){
        isRunning = false;
        return "exiting program";
    }

   /**
     * method for executing scripts
     * @param arg
     * @throws RecursiveScriptExecption
     */
    public String executeScript(String arg) throws RecursiveScriptExecption {
        /*if (scriptStack.contains(currentScriptFilePath)) throw new RecursiveScriptExecption();
        scriptStack.push(currentScriptFilePath);
        UserInputHandler process = new UserInputHandler(new FileInputHandler(arg));
        process.scriptMode(arg);
        scriptStack.pop();
        System.out.println("successfully executed script " + arg);*/
        return "";
    }

    /**
     * method for running program driven by user inputs
     */
    public abstract void consoleMode();

    /**
     * method for running program driven by inputs from file
     * @param path
     */
    public abstract void scriptMode(String path);

    public boolean hasCommand(String str){
        return map.containsKey(str);
    }
    public HashMap<String, Command> getMap() {
        return map;
    }

    public InputHandler getCurrentHandler() {
        return currentHandler;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setCurrentHandler(InputHandler currentHandler) {
        this.currentHandler = currentHandler;
    }

    public void setRunning(Boolean bool){
        isRunning = bool;
    }

    public void setPath(String currentScriptFilePath) {
        this.currentScriptFilePath = currentScriptFilePath;
    }

    public static Stack<String> getScriptStack() {
        return scriptStack;
    }
}
