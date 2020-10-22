/**
 * File: Dock.java
 * Date: August 26, 2019
 * @Author: Brian Rease
 * Purpose: This class represents specific docks contained at a SeaPort. Each Dock can have a Ship object docked at one time.
 * The class contains an overloaded toString method that is used when the internal data structure of the program is being sorted.
 */

package com.brianrease;

import java.util.Scanner;

public class Dock extends Thing {

    private Ship ship;

    //constructor
    public Dock(Scanner sc) {
        super(sc);
    } //end of constructor

    //getter and setter methods
    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }
    //end of getter and setter methods

    @Override
    public String toString() {
        String finalOutput = " Dock: " + super.toString();
        if(this.getShip() == null) {
            finalOutput += "\n  EMPTY";
        } else {
            finalOutput += "\n " + this.getShip().toString();
        }
        return finalOutput;
    } //end of toString()

    //Overload toString() with dummy parameter
    public String toString(int x) {
        return super.toString();
    } //end of overloaded toString()

} //end of Dock
