/**
 * File: PortTime.java
 * Date: August 26, 2019
 * @Author: Brian Rease
 * Purpose: This class is meant to represent the time at a specific SeaPort based on its location in the World.
 * This class is not used during Project 1 or Project 2 other than creating the class and its contents.
 */

package com.brianrease;

public class PortTime {

    private int time;

    //constructor
    public PortTime(int time) {
        this.time = time;
    } //end of constructor

    //getter and setter methods
    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
    //end of getter and setter methods

    @Override
    public String toString() {
        return "PortTime: " + this.getTime();
    } //end of toString()
} //end of PortTime