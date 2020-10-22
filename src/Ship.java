/**
 * File: Ship.java
 * Date: August 26, 2019
 * @Author: Brian Rease
 * Purpose: This class represents a Ship that can be docked at a Dock, or queued to dock at a Dock.
 * There are two types of Ship objects; PassengerShip and CargoShip. Each type of Ship has certain aspects related to their own type of object.
 * The class contains an overloaded toString method that is used when the internal data structure of the program is being sorted.
 *
 * The class is updated in Project 3 to include getter and setter methods for assigning a SeaPort and Dock to the Ship to allow a Ship to
 * leave a Dock once all of its Jobs are finished.
 */

package com.brianrease;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Ship extends Thing {

    private PortTime arrivalTime, dockTime;
    private double draft, length, weight, width;
    private ArrayList<Job> jobs;

    //adding for Project 3:
    private SeaPort port;
    private Dock dock;

    //constructor
    public Ship(Scanner sc, HashMap<Integer, SeaPort> seaPortHashMap, HashMap<Integer, Dock> dockHashMap) {
        super(sc);
        arrivalTime = null;
        dockTime = null;
        jobs = new ArrayList<>();
        if(sc.hasNextDouble()) {weight = sc.nextDouble();}
        if(sc.hasNextDouble()) {length = sc.nextDouble();}
        if(sc.hasNextDouble()) {width = sc.nextDouble();}
        if(sc.hasNextDouble()) {draft = sc.nextDouble();}

        this.setPort(seaPortHashMap, dockHashMap);  //used to set the SeaPort that a Ship is at
        this.setDock(dockHashMap);                  //used to set the Dock that a Ship is at, or set to null
    } //end of constructor

    //getter and setter methods
    public PortTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(PortTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public PortTime getDockTime() {
        return dockTime;
    }

    public void setDockTime(PortTime dockTime) {
        this.dockTime = dockTime;
    }

    public double getDraft() {
        return draft;
    }

    public void setDraft(double draft) {
        this.draft = draft;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public ArrayList<Job> getJobs() {
        return jobs;
    }

    public void setJobs(ArrayList<Job> jobs) {
        this.jobs = jobs;
    }

    //adding for Project 3:
    //The methods for getting and setting Ports and Docks are used for Project 3. They are mainly used
    //to help with the feature of a Ship leaving a Dock once its Jobs are finished.
    public SeaPort getPort() {
        return port;
    }

    public void setPort(HashMap<Integer, SeaPort> seaPortHashMap, HashMap<Integer, Dock> dockHashMap) {
        this.port = seaPortHashMap.get(this.getParent());
        if(this.port == null) {
            Dock myDock = dockHashMap.get(this.getParent());
            this.port = seaPortHashMap.get(myDock.getParent());
        }
    }

    public Dock getDock() {
        return dock;
    }

    public void setDock(HashMap<Integer, Dock> dockHashMap) {
        try {
            this.dock = dockHashMap.get(this.getParent());
        } catch(NullPointerException e) {
            this.dock = null;
        }
    }
    //end of getter and setter methods

    @Override
    public String toString() {
        String finalOutput = super.toString();
        return finalOutput;
    } //end of toString()

    //Overload toString() with dummy parameter
    public String toString(String attribute) {
        String output = "";

        switch(attribute) {
            case "name":
                output = super.toString();
                break;
            case "weight":
                output = super.toString() + " Weight: " + this.getWeight();
                break;
            case "length":
                output = super.toString() + " Length: " + this.getLength();
                break;
            case "width":
                output = super.toString() + " Width: " + this.getWidth();
                break;
            case "draft":
                output = super.toString() + " Draft: " + this.getDraft();
                break;
            default: break;
        } //end of switch
        return output;
    } //end of overloaded toString()
} //end of Ship