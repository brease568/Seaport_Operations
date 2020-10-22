/**
 * File: Person.java
 * Date: August 26, 2019
 * @Author: Brian Rease
 * Purpose: This class represents Persons associated to a SeaPort. Each Person has a 'skill' and will eventually
 * that 'skill' will fulfill a 'Job' requirement on a Ship.
 * The class contains an overloaded toString method that is used when the internal data structure of the program is being sorted.
 *
 * During Project 4 a Person is able to be set available or unavailable based on whether not their skill is being used
 * for a specific Job or not.
 */

package com.brianrease;

import java.util.HashMap;
import java.util.Scanner;

public class Person extends Thing {

    private String skill;
    private boolean availability;

    //constructor
    public Person(Scanner sc, HashMap<Integer, SeaPort> seaPortHashMap) {
        super(sc);
        if(sc.hasNext()) {
            skill = sc.next().trim();
        }
        availability = true;
        addPersonToPool(seaPortHashMap);
    } //end of constructor

    //getter and setter methods
    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public boolean isAvailable() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
    //end of getter and setter methods

    public void addPersonToPool(HashMap<Integer, SeaPort> seaPortHashMap) {
        SeaPort mySeaPort = seaPortHashMap.get(this.getParent());
        mySeaPort.getPersonResourcePool().add(this);
    } //end of addPersonToPool()



    @Override
    public String toString() {
        String finalOutput = "> Person: " + super.toString() + " " + this.getSkill();
        return finalOutput;
    } //end of toString()

    //Overload toString() with dummy parameter
    public String toString(int x) {
        return super.toString();
    } //end of overloaded toString()
} //end of Person
