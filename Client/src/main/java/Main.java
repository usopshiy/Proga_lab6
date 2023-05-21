import collection.RouteCollectionHandler;
import file.FileHandler;
import io.ConsoleInputHandler;
import io.InputHandler;
import io.UserInputHandler;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static utils.ConsoleColors.*;


public class Main {

    public static void main(String[] args) throws Exception{
        System.out.print("\t\t\t\t Lab5 v0.1\n");
        System.out.print(PURPLE_BOLD_BRIGHT+"""
              _____     ____    _    _   _______   ______    _____\s
             |  __ \\   / __ \\  | |  | | |__   __| |  ____|  / ____|
             | |__) | | |  | | | |  | |    | |    | |__    | (___ \s
             |  _  /  | |  | | | |  | |    | |    |  __|    \\___ \\\s
             | | \\ \\  | |__| | | |__| |    | |    | |____   ____) |
             |_|  \\_\\  \\____/   \\____/     |_|    |______| |_____/\s
            """+RESET);
        System.out.println("\t    by Egor Dashkevich aka usopshiy");
        FileHandler fileHandler = new FileHandler();
        RouteCollectionHandler collectionHandler = new RouteCollectionHandler();
        System.setErr(new PrintStream("file.txt", StandardCharsets.UTF_8));
        if (args.length!=0){
            fileHandler.setPath(args[0].trim());
            collectionHandler.deserializeCollection(fileHandler.read());
        }
        InputHandler inputHandler = new ConsoleInputHandler();
        UserInputHandler userInputHandler = new UserInputHandler(collectionHandler, fileHandler, inputHandler);
        userInputHandler.consoleMode();
    }
}
