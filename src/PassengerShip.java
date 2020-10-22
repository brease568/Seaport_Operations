/**
 * File: PassengerShip.java
 * Date: August 26, 2019
 * @Author: Brian Rease
 * Purpose: This class represents a PassengerShip which is a child of Ship. The PassengerShip contains information related to passengers and rooms on board.
 */

package com.brianrease;

import java.util.HashMap;
import java.util.Scanner;

public class PassengerShip extends Ship {

    private int numberOfOccupiedRooms, numberOfPassengers, numberOfRooms;

    //constructor
    public PassengerShip(Scanner sc, HashMap<Integer, SeaPort> seaPortHashMap, HashMap<Integer, Dock> dockHashMap) {
        super(sc, seaPortHashMap, dockHashMap);
        if(sc.hasNextInt()) {numberOfPassengers = sc.nextInt();}
        if(sc.hasNextInt()) {numberOfRooms = sc.nextInt();}
        if(sc.hasNextInt()) {numberOfOccupiedRooms = sc.nextInt();}
    } //end of constructor

    //getter and setter methods
    public int getNumberOfOccupiedRooms() {
        return numberOfOccupiedRooms;
    }

    public void setNumberOfOccupiedRooms(int numberOfOccupiedRooms) {
        this.numberOfOccupiedRooms = numberOfOccupiedRooms;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }
    //end of getter and setter methods

    @Override
    public String toString() {
        String finalOutput = " Passenger Ship: " + super.toString();
        return finalOutput;
    } //end of toString()
} //end of PassengerShip
