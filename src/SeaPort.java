/**
 * File: SeaPort.java
 * Date: August 26, 2019
 * @Author: Brian Rease
 * Purpose: This class is the representation of SeaPorts around the world.
 * It also creates ArrayLists of docks, ships, queued ships waiting to dock, and people(persons).
 * This class is responsible for the toString() method that generates a large chunk of the output data when scanning a file.
 * The class contains an overloaded toString method that is used when the internal data structure of the program is being sorted.
 */

package com.brianrease;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SeaPort extends Thing {

    private ArrayList<Dock> docks; //the list of docks at a SeaPort
    private ArrayList<Ship> que; //the list of ships waiting to dock
    private ArrayList<Ship> ships; //the list of all the ships at port
    private ArrayList<Person> persons; //the people with skills at this port

    //Adding an Object for Project 4 resources
    //private HashMap<Integer, ArrayList<Person>> personResourcePool;
    private ArrayList<Person> personResourcePool;

    //constructor
    public SeaPort(Scanner sc) {
        super(sc);
        docks = new ArrayList<>();
        que = new ArrayList<>();
        ships = new ArrayList<>();
        persons = new ArrayList<>();
        //personResourcePool = new HashMap<>();
        personResourcePool = new ArrayList<>();
    } //end of constructor

    //getter methods
    public ArrayList<Dock> getDocks() {
        return docks;
    }

    public ArrayList<Ship> getQue() {
        return que;
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

//    public HashMap<Integer, ArrayList<Person>> getPersonResourcePool() {
//        return personResourcePool;
//    }

    public ArrayList<Person> getPersonResourcePool() {
        return personResourcePool;
    }
    //end of getter methods

    //setter methods
    public void setDocks(ArrayList<Dock> docks) {
        this.docks = docks;
    }

    public void setQue(ArrayList<Ship> que) {
        this.que = que;
    }

    public void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }
    //end of setter methods

    @Override
    public String toString() {
        String finalOutput = "Seaport: " + super.toString() + "\n";
        for(Dock myDock : docks) {
            finalOutput += "\n" + myDock.toString() + "\n";
        }
        finalOutput += "\n\n --- List of all ships in que:";
        for(Ship myShip : que) {
            finalOutput += "\n >" + myShip.toString();
        }
        finalOutput += "\n\n --- List of all ships:";
        for(Ship myShip : ships) {
            finalOutput += "\n >" + myShip.toString();
        }
        finalOutput += "\n\n -- List of all persons:";
        for(Person myPerson : persons) {
            finalOutput += "\n >" + myPerson.toString();
        }
        return finalOutput + "\n";
    } //end of toString()

    //Overload toString() with dummy parameter
    public String toString(int x) {
        return super.toString();
    } //end of overloaded toString()

} //end of SeaPort
