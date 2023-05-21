package io;

import data.Coordinates;
import data.Location;
import data.Route;
import exceptions.InvalidDataException;

import java.util.Scanner;

/**
 * abstract class for dealing with inputs
 */
public abstract class InputHandler {
    private final Scanner scanner;

    public InputHandler(Scanner sc){
        this.scanner = sc;
        this.scanner.useDelimiter("\n");
    }

    public Scanner getScanner() {
        return scanner;
    }

    public String readName() throws InvalidDataException {
        String s = scanner.nextLine().trim();
        if (s.equals("")){
            throw new InvalidDataException("route name cannot be empty");
        }
        return s;
    }

    public Coordinates readCoordinates() throws InvalidDataException{
        float x = readCordX();
        Integer y = readCordY();
        return new Coordinates(x, y);
    }

    public Location readLocation() throws InvalidDataException{
        Integer x = readLocX();
        Double y = readLocY();
        Long z = readLocZ();
        String name = readLocName();
        return new Location(x, y, z, name);
    }

    public float readCordX() throws InvalidDataException{
        float x;
        try{
            x = Float.parseFloat(scanner.nextLine().trim());}
        catch(NumberFormatException e){
            throw new InvalidDataException("invalid coordinate format");
        }
        if (x > 162){
            throw new InvalidDataException("must be less than 162");
        }
        return x;
    }

    public Integer readCordY() throws InvalidDataException{
        Integer y;
        try{
            y = Integer.parseInt(scanner.nextLine().trim());
        }
        catch(NumberFormatException e){
            throw new InvalidDataException("invalid coordinate format");
        }
        if (y > 57){
            throw new InvalidDataException("must be less than 57");
        }
        return y;
    }

    public Integer readLocX() throws InvalidDataException{
        Integer x;
        try{
            x = Integer.parseInt(scanner.nextLine().trim());
        }
        catch(NumberFormatException e){
            throw new InvalidDataException("invalid coordinate format");
        }
        return x;
    }

    public Double readLocY() throws InvalidDataException{
        Double y;
        try{
            y = Double.parseDouble(scanner.nextLine().trim());
        }
        catch(NumberFormatException e){
            throw new InvalidDataException("invalid coordinate format");
        }
        return y;
    }

    public Long readLocZ() throws InvalidDataException{
        Long z;
        try{
            z = Long.parseLong(scanner.nextLine().trim());
        }
        catch(NumberFormatException e){
            throw new InvalidDataException("invalid coordinate format");
        }
        return z;
    }

    public String readLocName() throws InvalidDataException{
        String n = scanner.nextLine().trim();
        if (n.length() > 457){
            throw new InvalidDataException("Location name cannot be longer than 457");
        }
        return n;
    }

    public Double readDistance() throws InvalidDataException{
        Double dist = null;
        String arg = scanner.nextLine().trim();
        if(!arg.equals("")) {
            try {
                dist = Double.parseDouble(arg);
            } catch (NumberFormatException e) {
                throw new InvalidDataException("invalid coordinate format");
            }
            if (dist <= 1) {
                throw new InvalidDataException("distance must be greater than 1");
            }
        }
        return dist;
    }
    public Route readRoute() throws InvalidDataException{
        String name = readName();
        Coordinates cords = readCoordinates();
        Location from = readLocation();
        Location to = readLocation();
        Double distance = readDistance();
        return new Route(name, cords, from, to, distance);
    }

    public CommandWrapper readCommand(){
        String cmd = scanner.nextLine().trim();
        if(cmd.contains(" ")){
            String[] arr = cmd.split(" ");
            cmd = arr[0];
            String arg = arr[1];
            return new CommandWrapper(cmd, arg);
        }
        else{
            return new CommandWrapper(cmd);
        }
    }
}
