package io;

import commands.*;
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

    /**
     * constructor, sets fields and standard commands
     */
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
     * method for executing commands
     */
    public void runCommand(String key, String arg) {
        try {
            if (!map.containsKey(key)) {
                throw new InvalidDataException(key + ": this command doesn't exist");
            }
            map.get(key).execute(arg);
        } catch (InvalidDataException | CommandException e) {
            ExceptionWrapper.outException(e.getMessage());
        }
    }

    /**
     * exits program without saving collection
     */
    public void exit(){
        isRunning = false;
    }

   /**
     * method for executing scripts
     * @param arg
     * @throws RecursiveScriptExecption
     */
    public void executeScript(String arg) throws RecursiveScriptExecption {
        /*if (scriptStack.contains(currentScriptFilePath)) throw new RecursiveScriptExecption();
        scriptStack.push(currentScriptFilePath);
        UserInputHandler process = new UserInputHandler(new FileInputHandler(arg));
        process.scriptMode(arg);
        scriptStack.pop();
        System.out.println("successfully executed script " + arg);*/
    }

    /**
     * method for running program driven by user inputs
     */
    public  void consoleMode(){
        isRunning = true;
        while(isRunning){
            System.out.print("enter command: ");
            CommandWrapper pair = currentHandler.readCommand();
            runCommand(pair.getCommand(), pair.getArg());
            }
        }

    /**
     * method for running program driven by inputs from file
     * @param path
     */
    public void scriptMode(String path){
        currentScriptFilePath = path;
        isRunning = true;
        while(isRunning && currentHandler.getScanner().hasNextLine()){
            CommandWrapper pair = currentHandler.readCommand();
            runCommand(pair.getCommand(), pair.getArg());
        }
    }

    public boolean hasCommand(String str){
        return map.containsKey(str);
    }
    public HashMap<String, Command> getMap() {
        return map;
    }

    public InputHandler getCurrentHandler() {
        return currentHandler;
    }

}
