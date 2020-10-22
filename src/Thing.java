/**
 * File: Thing.java
 * Date: August 26, 2019
 * @Author: Brian Rease
 * Purpose: This class is the origin of all the objects in the World. Every object will include a name, index, and parent.
 * This class implements Comparable and is used to compare different Things by name. The class further creates several
 * inner classes that implement the Comparator class. These inner classes are used to compare Ships by weight, length,
 * width and draft.
 */

package com.brianrease;

import java.util.Comparator;
import java.util.Scanner;

public class Thing implements Comparable<Thing> {

    private int index, parent;
    private String name;

    //constructor
    public Thing(String name, int index, int parent) {
        this.name = name;
        this.index = index;
        this.parent = parent;
    } //end of constructor

    //overloaded constructor that accepts Scanner content that goes on to call the constructor above
    public Thing(Scanner sc) {
        this(sc.next().trim(), sc.nextInt(), sc.nextInt());
    }

    //getter methods
    public int getIndex() {
        return index;
    }

    public int getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }
    //end of getter methods

    //This setter method is added for use in Project 3 when a ship is leaving a Dock and a new Ship is having its parent index set.
    public void setParent(int parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return name + " " + index;
    } //end of toString()

    //Compare and Comparator methods and inner classes called when sorting data in the internal data structures.
    @Override
    public int compareTo(Thing thing) {
        return this.getName().compareTo(thing.getName());
    }

    class NameCompare implements Comparator<Thing> {
        public int compare(Thing myThing1, Thing myThing2) {
            return myThing1.getName().compareTo(myThing2.getName());
        }
    }

    class WeightCompare implements Comparator<Ship> {
        public int compare(Ship ship1, Ship ship2) {
            if(ship1.getWeight() < ship2.getWeight()) return -1;
            if(ship1.getWeight() > ship2.getWeight()) return 1;
            else return 0;
        }
    }

    class LengthCompare implements Comparator<Ship> {
        public int compare(Ship ship1, Ship ship2) {
            if(ship1.getLength() < ship2.getLength()) return -1;
            if(ship1.getLength() > ship2.getLength()) return 1;
            else return 0;
        }
    }

    class WidthCompare implements Comparator<Ship> {
        public int compare(Ship ship1, Ship ship2) {
            if(ship1.getWidth() < ship2.getWidth()) return -1;
            if(ship1.getWidth() > ship2.getWidth()) return 1;
            else return 0;
        }
    }

    class DraftCompare implements Comparator<Ship> {
        public int compare(Ship ship1, Ship ship2) {
            if(ship1.getDraft() < ship2.getDraft()) return -1;
            if(ship1.getDraft() > ship2.getDraft()) return 1;
            else return 0;
        }
    }
} //end of Thing