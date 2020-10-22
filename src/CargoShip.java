/**
 * File: CargoShip.java
 * Date: August 26, 2019
 * @Author: Brian Rease
 * Purpose: This class represents a CargoShip which is a child of Ship. The CargoShip contains information related
 * to weight, volume, and value of it's cargo.
 */

package com.brianrease;

import java.util.HashMap;
import java.util.Scanner;

public class CargoShip extends Ship {

    private double cargoValue, cargoVolume, cargoWeight;

    //constructor
    public CargoShip(Scanner sc, HashMap<Integer, SeaPort> seaPortHashMap, HashMap<Integer, Dock> dockHashMap) {
        super(sc, seaPortHashMap, dockHashMap);
        if(sc.hasNextDouble()) {cargoWeight = sc.nextDouble();}
        if(sc.hasNextDouble()) {cargoVolume = sc.nextDouble();}
        if(sc.hasNextDouble()) {cargoValue = sc.nextDouble();}
    } //end of constructor

    //getter and setter methods
    public double getCargoValue() {
        return cargoValue;
    }

    public void setCargoValue(double cargoValue) {
        this.cargoValue = cargoValue;
    }

    public double getCargoVolume() {
        return cargoVolume;
    }

    public void setCargoVolume(double cargoVolume) {
        this.cargoVolume = cargoVolume;
    }

    public double getCargoWeight() {
        return cargoWeight;
    }

    public void setCargoWeight(double cargoWeight) {
        this.cargoWeight = cargoWeight;
    }
    //end of getter and setter methods

    @Override
    public String toString() {
        String finalOutput = " Cargo Ship: " + super.toString();
        return finalOutput;
    } //end of toString()
} //end of CargoShip
