package io;

import collection.RouteCollectionHandler;
import commands.*;
import file.FileHandler;
import exceptions.*;

import java.util.HashMap;
import java.util.Stack;

/**
 * class that runs programm
 */
public class UserInputHandler {
    private final HashMap<String, Command> map;
    private final RouteCollectionHandler collectionHandler;
    private final InputHandler currentHandler;
    private final FileHandler fileHandler;
    private String currentScriptFilePath = "";
    private boolean isRunning = false;

    private static final Stack<String> scriptStack = new Stack<>();

    /**
     * constructor, sets fields and standard commands
     * @param collectionHandler
     * @param fileHandler
     * @param inputHandler
     */
    public UserInputHandler(RouteCollectionHandler collectionHandler, FileHandler fileHandler, InputHandler inputHandler){
        this.collectionHandler = collectionHandler;
        this.fileHandler = fileHandler;
        this.currentHandler = inputHandler;

        map = new HashMap<>();
        addCommand("help", new Help(this.map));
        addCommand("info", new Info(this.collectionHandler));
        addCommand("show", new Show(this.collectionHandler));
        addCommand("add", new Add(this.collectionHandler, currentHandler));
        addCommand("update_by_id", new UpdateByID(this.collectionHandler, this.currentHandler));
        addCommand("remove_by_id", new RemoveByID(this.collectionHandler));
        addCommand("clear", new Clear(collectionHandler));
        addCommand("save", new Save(fileHandler, collectionHandler));
        addCommand("exit", new Exit(this));
        addCommand("add_if_max", new AddIfMax(this.currentHandler, this.collectionHandler));
        addCommand("add_if_min", new AddIfMin(this.currentHandler, this.collectionHandler));
        addCommand("remove_greater", new RemoveGreater(this.currentHandler, this.collectionHandler));
        addCommand("remove_any_by_distance", new RemoveAnyByDistance(this.collectionHandler));
        addCommand("group_counting_by_from", new GroupCountingByFrom(this.collectionHandler));
        addCommand("execute_script", new ExecuteScript(this));
        addCommand("print_ascending", new PrintAscending(this.collectionHandler));
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
     * @param key
     * @param arg
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
        if (scriptStack.contains(currentScriptFilePath)) throw new RecursiveScriptExecption();
        scriptStack.push(currentScriptFilePath);
        UserInputHandler process = new UserInputHandler(collectionHandler, fileHandler, new FileInputHandler(arg));
        process.scriptMode(arg);
        scriptStack.pop();
        System.out.println("successfully executed script " + arg);
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
}
