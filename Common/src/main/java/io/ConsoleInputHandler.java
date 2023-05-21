package io;

import data.Coordinates;
import data.Location;
import data.Route;

import java.util.Scanner;

/**
 * Input Handler for user input in console
 */
public class ConsoleInputHandler extends InputHandler {
    public ConsoleInputHandler(){
        super(new Scanner(System.in));
        getScanner().useDelimiter("\n");
    }

    @Override
    public String readName(){
        return new Request<>("name:", super::readName).getRespond();
    }

    @Override
    public Coordinates readCoordinates(){
        return new Request<>("enter coordinates data:\n", super::readCoordinates).getRespond();
    }

    @Override
    public Location readLocation(){
        return  new Request<>("enter location data:\n", super::readLocation).getRespond();
    }

    @Override
    public float readCordX(){
        return new Request<>("x coordinate:", super::readCordX).getRespond();
    }

    @Override
    public Integer readCordY(){
        return new Request<>("y coordinate:", super::readCordY).getRespond();
    }

    @Override
    public  Integer readLocX(){
        return new Request<>("x coordinate:", super::readLocX).getRespond();
    }

    @Override
    public  Double readLocY(){
        return new Request<>("y coordinate:", super::readLocY).getRespond();
    }

    @Override
    public  Long readLocZ(){
        return  new Request<>("z coordinate:", super::readLocZ).getRespond();
    }

    @Override
    public  String readLocName(){
        return new Request<>("name:", super::readLocName).getRespond();
    }

    @Override
    public Double readDistance(){
        return new Request<>("enter distance:", super::readDistance).getRespond();
    }

    @Override
    public Route readRoute(){
        return new Request<>("enter Route data: \n", super::readRoute).getRespond();
    }
}
