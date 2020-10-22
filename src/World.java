/**
 * File: World.java
 * Date: August 26, 2019
 * @Author: Brian Rease
 * Purpose: This class is the central class that stores and organizes all of the other 'Thing' objects.
 * This class will represent the world as it exists to the user based off of the data file selected.
 * The class has a method to accept a File that goes on to pass each line from the file to a data processing method, 'processData'.
 * The data processing method contains functionality to create objects and add them to ArrayLists and attach objects to parent objects if applicable.
 * Project 2 added two methods used for sorting the data structures; the two classes sort Things by name and sort Ships by different attributes.
 */

package com.brianrease;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class World extends Thing {

    private ArrayList<SeaPort> ports;
    private PortTime time;

    //creating an arrayList to hold everything in the world
    private ArrayList<Thing> allThings;

    //constructor
    public World(String name, int index, int parent, File dataFile) {
        super(name, index, parent);
        ports = new ArrayList<>();
        allThings = new ArrayList<>();
        initialScannerOfDataFile(dataFile);
        //time = new PortTime(); //commented out for now because the constructor calls for an Integer and there is no mention of how else to implement this yet
    } //end of constructor

    //setters
    public void setAllThings(ArrayList<Thing> allThings) {
        this.allThings = allThings;
    }

    public void setPorts(ArrayList<SeaPort> ports) {
        this.ports = ports;
    }
    //end of setter methods

    //getters
    public ArrayList<SeaPort> getPorts() {
        return ports;
    }

    public ArrayList<Thing> getAllThings() {
        return allThings;
    }
    //end of getter methods

    //method to read the data file and start to create the data structures
    public void initialScannerOfDataFile(File currentFile) {
        try {
            Scanner sc = new Scanner(currentFile);

            //Create the HashMaps that will be added to for SeaPorts, Docks, and Ships - added during Project 2
            HashMap<Integer, SeaPort> mySeaPortMap = new HashMap<>();
            HashMap<Integer, Dock> myDockMap = new HashMap<>();
            HashMap<Integer, Ship> myShipMap = new HashMap<>();

            while (sc.hasNextLine()) {
                String currentLine = sc.nextLine();
                processData(currentLine, mySeaPortMap, myDockMap, myShipMap);
            }
        } catch (Exception e3) {
            JOptionPane.showMessageDialog(null, "Something wrong with initial scanner!\n\n" + e3.getMessage());
        }
    } //end of initialScanner()

    //method to process each line in the data file. Switch case on the first word on a line.
    private void processData(String currentLine, HashMap<Integer, SeaPort> mySeaPortMap, HashMap<Integer, Dock> myDockMap, HashMap<Integer, Ship> myShipMap) {
        if(currentLine.equals("")) {
            return;
        } else if (currentLine.startsWith("//")) {
            return;
        }
        try {
            Scanner secondScanner = new Scanner(currentLine);

            switch(secondScanner.next().trim().toLowerCase()) {
                case "port":
                    SeaPort newSeaPort = new SeaPort(secondScanner);
                    this.getAllThings().add(newSeaPort);
                    this.getPorts().add(newSeaPort);
                    mySeaPortMap.put(newSeaPort.getIndex(), newSeaPort);
                    break;
                case "dock":
                    Dock newDock = new Dock(secondScanner);
                    this.getAllThings().add(newDock);
                    myDockMap.put(newDock.getIndex(), newDock);
                    this.getSeaPortByIndex(newDock.getParent(), mySeaPortMap).getDocks().add(newDock);
                    break;
                case "cship":
                    CargoShip newCargoShip = new CargoShip(secondScanner, mySeaPortMap, myDockMap);
                    this.getAllThings().add(newCargoShip);
                    myShipMap.put(newCargoShip.getIndex(), newCargoShip);
                    this.assignShipToParent(newCargoShip, myDockMap, mySeaPortMap);
                    break;
                case "pship":
                    PassengerShip newPassengerShip = new PassengerShip(secondScanner, mySeaPortMap, myDockMap);
                    this.getAllThings().add(newPassengerShip);
                    myShipMap.put(newPassengerShip.getIndex(), newPassengerShip);
                    this.assignShipToParent(newPassengerShip, myDockMap, mySeaPortMap);
                    break;
                case "person":
                    Person newPerson = new Person(secondScanner, mySeaPortMap);
                    this.getAllThings().add(newPerson);
                    this.getSeaPortByIndex(newPerson.getParent(), mySeaPortMap).getPersons().add(newPerson);
                    break;
                case "job": //This functionality will work if there is a Ship that is the parent of the Job. However, will throw a NullPointerException otherwise.
                    Job newJob = new Job(secondScanner, myShipMap, SeaPortProgram.getJobPanel());
                    this.getAllThings().add(newJob);
                    this.assignJobToShip(newJob, myShipMap); //'Graceful' handing is done by catching NullPointerException.
                    break;
                default : JOptionPane.showMessageDialog(null, "Something is wrong on the line: \n\n" + currentLine + "\n\nVerify you have a correctly formatted data file!"); break;
            }
        } catch(NullPointerException eNull) {
            /*This output to the console is currently 'gracefully' handling in most cases a Job not having a Ship to be assigned to.
            This can be updated in the next project where I assume something will happen with the Job. OR I can assume a Job will always
            have a Ship to be assigned to under the assumption the data file is formatted correctly as stated.*/
            System.out.println("Some 'Thing' was currently null.\n> " + eNull.getStackTrace());
        }
        catch(Exception e4) {
            JOptionPane.showMessageDialog(null, "Something went wrong with processing data!\n\n" + e4.getMessage());
        } //end of catch()
    } //end of processData()

    //take a newShip and assign it to either a dock or a port
    private void assignShipToParent(Ship newShip, HashMap<Integer, Dock> dockHashMap, HashMap<Integer, SeaPort> seaPortHashMap) {
        Dock myDock = getDockByIndex(newShip.getParent(), dockHashMap);
        if(myDock == null) { //if the parent (dock) is null then it has to be assigned to a port. Also is added to Que which is ships waiting to dock.
            getSeaPortByIndex(newShip.getParent(), seaPortHashMap).getShips().add(newShip);
            getSeaPortByIndex(newShip.getParent(), seaPortHashMap).getQue().add(newShip);
            return;
        } else { //otherwise, the ship is going to be assigned to a dock
            myDock.setShip(newShip);
            getSeaPortByIndex(myDock.getParent(), seaPortHashMap).getShips().add(newShip);
        }
    } //end of assignShipToParent()

    //This method calls getShipByIndex and should assign a job to a ship
    private void assignJobToShip(Job newJob, HashMap<Integer, Ship> shipHashMap) {
        Ship myShip = getShipByIndex(newJob.getParent(), shipHashMap);
        myShip.getJobs().add(newJob);
    } //end of assignJobToShip()

    private Ship getShipByIndex(int x, HashMap<Integer, Ship> shipHashMap) {
        return shipHashMap.get(x);
    }

    private SeaPort getSeaPortByIndex(int x, HashMap<Integer, SeaPort> seaPortMap) {
        return seaPortMap.get(x);
    }

    private Dock getDockByIndex(int x, HashMap<Integer, Dock> dockHashMap) {
        return dockHashMap.get(x);
    }

    /* Adding a method for sorting by Name of Thing
    This method was especially difficult. Each class that has a toString() method besides 'Thing.java' has an overloaded toString()
    method that takes a dummy parameter 'x'. Initially each case of thingToSort was going to be stored in an ArrayList<?> listOfThings
    and then a for loop of the nature 'for(Object myThing : listOfThings)' would iterate through the objects and call the overloaded
    toString method. However, it is not possible to overload toString in the manner of using Object instead of a specific SeaPort, Dock,
    etc. Therefore, the for loop has to be implemented in each switch case specific to that objects type. In later updates this could be updated
    to have a more elegant design.
     */
    public String sortMyThingByName(String thingToSort) {
        thingToSort = thingToSort.trim().toLowerCase();
        //Using StringBuilder instead of doing += to a String inside of a for loop
        StringBuilder result = new StringBuilder("Sort Results: \n\n");

        //Variable created as a dummy variable to pass to overloaded toString() methods
        int x = 0;

        if(thingToSort.equals("seaport")) {
            Collections.sort(ports);
            result.append("Sea Ports:\n");
            for(SeaPort myThing : ports) {
                result.append("\n> " + myThing.toString(x));
            }
        } else {
            for(SeaPort mySeaPort : ports) {
                switch(thingToSort) {
                    case "dock":
                        result.append("\n\nSea Port: " + mySeaPort.toString(x) + "\n> Docks:");
                        Collections.sort(mySeaPort.getDocks());
                        for(Dock myThing : mySeaPort.getDocks()) {
                            result.append("\n>> " + myThing.toString(x));
                        }
                        break;
                    case "ship":
                        result.append("\n\nSea Port: " + mySeaPort.toString(x) + "\n> Ships:");
                        Collections.sort(mySeaPort.getShips());
                        for(Ship myThing : mySeaPort.getShips()) {
                            result.append("\n>> " + myThing.toString());
                        }
                        break;
                    case "job": //things might get spicy here
                        result.append("\n\nSea Port: " + mySeaPort.toString(x) + "\n> Jobs:");
                        for(Ship myShip : mySeaPort.getShips()) {
                            Collections.sort(myShip.getJobs());
                            for(Job myJob : myShip.getJobs()) {
                                result.append("\n>> " + myJob.toString(x));
                            }
                        }
                        break;
                    case "person":
                        result.append("\n\nSea Port: " + mySeaPort.toString(x) + "\n> Persons:");
                        Collections.sort(mySeaPort.getPersons());
                        for(Person myThing : mySeaPort.getPersons()) {
                            result.append("\n>> " + myThing.toString(x));
                        }
                        break;
                    default: break;
                } //end of switch
            } //end of for loop for iterating through ports
        } //end of else
        return result.toString();
    } //end of sortMyThingByName()

    //This method is called when a user attempts to Sort a Ship by any attribute.
    public String sortShipByAttribute(String attribute) {
        attribute = attribute.trim().toLowerCase();
        StringBuilder result = new StringBuilder("Sort Results: \n\n");

        //Creating variables to help distinguish which toString() method to call
        String helperAttribute = "";
        int x = 0;

        for(SeaPort mySeaPort : ports) {
            switch(attribute) {
                case "name":
                    helperAttribute = "name";
                    Collections.sort(mySeaPort.getShips(), new NameCompare());
                    for (Ship myShip : mySeaPort.getShips()) {
                        result.append(myShip.toString(helperAttribute));
                    }
                    break;
                case "weight":
                    helperAttribute = "weight";
                    result.append("\n\nSea Port: " + mySeaPort.toString(x) + "\n> Ships sorted by Weight:");
                    Collections.sort(mySeaPort.getShips(), new WeightCompare());
                    for (Ship myShip : mySeaPort.getShips()) {
                        result.append("\n>> " + myShip.toString(helperAttribute));
                    }
                    break;
                case "length":
                    helperAttribute = "length";
                    result.append("\n\nSea Port: " + mySeaPort.toString(x) + "\n> Ships sorted by Length:");
                    Collections.sort(mySeaPort.getShips(), new LengthCompare());
                    for(Ship myShip : mySeaPort.getShips()) {
                        result.append("\n>> " + myShip.toString(helperAttribute));
                    }
                    break;
                case "width":
                    helperAttribute = "width";
                    result.append("\n\nSea Port: " + mySeaPort.toString(x) + "\n> Ships sorted by Width:");
                    Collections.sort(mySeaPort.getShips(), new WidthCompare());
                    for(Ship myShip : mySeaPort.getShips()) {
                        result.append("\n>> " + myShip.toString(helperAttribute));
                    }
                    break;
                case "draft":
                    helperAttribute = "draft";
                    result.append("\n\nSea Port: " + mySeaPort.toString(x) + "\n> Ships sorted by Draft:");
                    Collections.sort(mySeaPort.getShips(), new DraftCompare());
                    for(Ship myShip : mySeaPort.getShips()) {
                        result.append("\n>> " + myShip.toString(helperAttribute));
                    }
                    break;
                default: break;
            } //end of switch
        } //end of for loop of Sea Ports
        return result.toString();
    } //end of sortShipByAttribute()

    @Override
    public String toString() {
        String finalOutput = ">>>>> The World: \n\n\n";
        for(SeaPort allSeaPorts : ports) {
            finalOutput += allSeaPorts.toString() + "\n";
        }
        return finalOutput;
    } //end of toString()
} //end of World