/**
 * File: Job.java
 * Date: August 26, 2019
 * @Author: Brian Rease
 * Purpose: This class is meant to represent specific jobs that need to be filled on a Ship. Each Job has specific requirements that
 * must be filled to complete the Job.
 * The class contains an overloaded toString method that is used when the internal data structure of the program is being sorted.
 *
 * This class is the focal point of Project 3. It also is the main implementation of concurrency in the SeaPort Program so far.
 * When a new Job is created a new Thread is assigned to that Job. The Threads call the overridden run() method based on the Job
 * class implementing the Runnable interface. The code also implements a feature to allow a Ship to leave a parent Dock once all of its
 * Jobs are completed.
 *
 * During Project 4 the method isWaiting() is ultimately configured to not only determine if a Ship is at a Dock, but is also used
 * to determine whether or not a Job is able to progress based on if it has the available resources (Person with a skill).
 */

package com.brianrease;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Job extends Thing implements Runnable {

    //These Objects are already here:
    private double duration;
    private ArrayList<String> requirements; //should be some of the skills of the persons

    //Adding object for Project 3:
    private JPanel seaPortJobPanel;                      //This JPanel is assigned to the one passed into the constructor
    private ArrayList<Person> workers;                 //I think this is for Project 4

    //GUI elements:
    private JProgressBar jobProgressBar;
    private boolean goFlag = true, noKillFlag = true;
    private JButton jobToggle = new JButton("Suspend");
    private JButton jobKill = new JButton("Cancel");

    enum Status {RUNNING, SUSPENDED, WAITING, DONE, IMPOSSIBLE}
    private Status status = Status.SUSPENDED;

    //adding a thread for a Job
    private Thread jobThread;

    //adding objects for a jobs parent ship and the port
    private Ship jobParentShip;
    private SeaPort parentSeaPort;

    //flag for if jobs are finished
    private boolean jobIsFinished;

    //for if a job is impossible:
    private boolean impossibleFlag;
    //working people to keep track of
    private ArrayList<Person> workingPeople;
    private ArrayList<String> allPossibleSkills;
    private ArrayList<String> availableSkills;
    private StringBuilder myBuilder;


    //constructor
    public Job(Scanner sc, HashMap<Integer, Ship> shipHashMap, JPanel jobPanel) {
        super(sc);
        requirements = new ArrayList<>();
        if(sc.hasNextDouble()) {duration = sc.nextDouble();}
        while(sc.hasNext()) { //this while loop is used because a Job could have several requirements.
            requirements.add(sc.next().trim());
        }

        seaPortJobPanel = jobPanel;
        jobProgressBar = new JProgressBar();
        jobProgressBar.setStringPainted(true);

        seaPortJobPanel.add(jobProgressBar);
        seaPortJobPanel.add(new JLabel("Job Name: ", SwingConstants.CENTER));  //this is a placeholder for project 4
        seaPortJobPanel.add(new JLabel(this.getName(), SwingConstants.CENTER));

        seaPortJobPanel.add(jobToggle);
        seaPortJobPanel.add(jobKill);

        jobParentShip = shipHashMap.get(this.getParent());
        parentSeaPort = jobParentShip.getPort();

        jobIsFinished = false;

        //actionListeners for the JButtons on the Job Panel
        jobToggle.addActionListener(event -> toggleJob());
        jobKill.addActionListener(event -> toggleKill());

        myBuilder = new StringBuilder();
        impossibleFlag = false;
        workers = new ArrayList<>();
        workingPeople = new ArrayList<>();
        allPossibleSkills = new ArrayList<>();
        availableSkills = new ArrayList<>();

        //Each job will have it's own Thread and immediately start at the end of its creation
        jobThread = new Thread(this, this.getName());
        jobThread.start();
    } //end of constructor

    //getter and setter methods
    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public ArrayList<String> getRequirements() {
        return requirements;
    }

    public void setRequirements(ArrayList<String> requirements) {
        this.requirements = requirements;
    }

    public synchronized JTextArea getResourceOutputArea() {
        return SeaPortProgram.getResourceOutputArea();
    }
    //end of getter and setter methods

    //Adding code for Project 3:
    //Adding methods for toggling the JButtons to Suspend and Cancel jobs
    private void toggleJob() {
        goFlag = !goFlag;
    }

    private void toggleKill() {
        noKillFlag = false;
        jobKill.setBackground(Color.RED);
    }

    //Status method to change button colors:
    private void showStatus(Status jobStatus) {
        status = jobStatus;

        switch(status) {
            case RUNNING:
                jobToggle.setBackground(Color.GREEN);
                jobToggle.setText("Running");
                break;
            case SUSPENDED:
                jobToggle.setBackground(Color.YELLOW);
                jobToggle.setText("Suspended");
                break;
            case WAITING:
                jobToggle.setBackground(Color.ORANGE);
                jobToggle.setText("Waiting");
                break;
            case DONE:
                jobToggle.setBackground(Color.RED);
                jobToggle.setText("Done");
                break;
            case IMPOSSIBLE:
                jobToggle.setBackground(Color.DARK_GRAY);
                jobToggle.setText("Impossible");
                //default: break;
        } //end of switch()
    } //end of showStatus()

    //This method is the main method called once a Job is started. It determines if a Job is place in a waiting state or if the Job can progress.
    //It also determines whether or not a Job is IMPOSSIBLE or not.
    //The method will set a Person as unavailable if the Job is going to use the resource.
    private synchronized boolean isWaiting() {
        if(this.parentSeaPort.getQue().contains(jobParentShip)) { //if the ship doesn't have a dock
            return true;
        }

        if(this.getRequirements().isEmpty()) { //if the job has no requirements
            return false;
        } else if (!this.getRequirements().isEmpty()) {
            workers = parentSeaPort.getPersonResourcePool();

            if(workers.isEmpty()) {
                return true;
            }
        }

        for(Person myPerson : workers) { //walk through everyone in workers and add their skill to allSkills as well as determine if they are going to 'work ' or not
            allPossibleSkills.add(myPerson.getSkill());
            if(myPerson.isAvailable()) {
                availableSkills.add(myPerson.getSkill());
                workingPeople.add(myPerson);
            }
        }

        for(String requiredSkill : requirements) { //if the skill is never going to be available make the Job IMPOSSIBLE
            if(!allPossibleSkills.contains(requiredSkill)) {
                impossibleFlag = true;
                return true;
            }

            if (!availableSkills.contains(requiredSkill)) { //this if statement is reached if a Job is most likely going to be able to progress
                return true;
            } else if (availableSkills.contains(requiredSkill)){
                for(Person myPerson : workers) {
                    if(requirements.contains(myPerson.getSkill())) {
                        myPerson.setAvailability(false);
                        //this.getResourceOutputArea().append("> " + jobThread.getName() + " is using worker " + myPerson + ".\n");
                        myBuilder.append("> " + jobThread.getName() + " is using worker " + myPerson + ".\n");
                    } else {
                        workingPeople.remove(myPerson);
                    }
                } //end of for Person
            }
        }
        return false; //false is ultimately called when this method is finished unless one of the 'edge' cases is met
    } //end of isWaiting()2

    //This method is called from a jobThread to determine if a Ship has completed all of its jobs
    private boolean shipsJobsFinished() {
        for(Job myJob : jobParentShip.getJobs()) {
            if(myJob.jobIsFinished) {
                return true;
            }
        }
        return false;
    }

    //This method removes a Ship from a dock and assigns a new Ship from the queue to that Dock.
    private void shipLeaveDock() {
        jobParentShip.setParent(0);

        try {
            jobParentShip.getDock().setShip(null);
        } catch(NullPointerException ignore) {

        }

        if(!parentSeaPort.getQue().isEmpty()) {
            Ship shipToDock = parentSeaPort.getQue().remove(0);
            jobParentShip.getDock().setShip(shipToDock);
            shipToDock.setParent(jobParentShip.getIndex());
            if(shipToDock.getJobs().isEmpty()) {
                shipLeaveDock();
            }
        }
    }

    //implementing the must have method run()
    @Override
    public void run() {
        long time = System.currentTimeMillis();
        long startTime = time;
        double stopTime = time + 1000 * duration;
        double runDuration = stopTime - startTime;
        //SeaPortProgram.getResourceOutputArea().append("> " + jobThread.getName() + " thread is starting run() method.\n");
        //this.getResourceOutputArea().append("> " + jobThread.getName() + " thread is starting run() method.\n");
        myBuilder.append("> " + jobThread.getName() + " thread is starting run() method.\n");

        //Synchronize on a SeaPort and determine whether or not a jobThread should start in a waiting state based on if it is
        //in a Queue. If it is in a Que then put the jobThread in a waiting state
        synchronized (parentSeaPort) {
            while(this.isWaiting()) {
                try {
                    if(impossibleFlag) {
                        showStatus(Status.IMPOSSIBLE);
                        //SeaPortProgram.getResourceOutputArea().append("!! > " + jobThread.getName() + " thread is impossible to complete!!\n");
                        //this.getResourceOutputArea().append("!! > " + jobThread.getName() + " thread is impossible to complete!!\n");
                        myBuilder.append("!! > " + jobThread.getName() + " thread is impossible to complete!!\n");
                        jobThread.interrupt();
                        return;
                    }
                    showStatus(Status.WAITING);
                    this.parentSeaPort.wait();
                } catch(InterruptedException e) {
                    System.out.println("Error >> " + e);
                } //end of catch()
            } //end of while loop
        } //end of synchronized

        //This is the main chunk of code that progresses a jobThread in the GUI. A jobThread only progresses
        //if it has passed the above code of whether the jobs parent Ship is in a Que or not.
        while ((time < stopTime) && noKillFlag) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                //nothing
            }
            if (goFlag) {
                showStatus(Status.RUNNING);
                time += 100;
                jobProgressBar.setValue((int) (((time - startTime) / runDuration) * 100));
            } else {
                showStatus(Status.SUSPENDED);
            } //end of inner if/else
        } //end of while()

        //Once a jobThread is finished it will set the progress to 100 and mark the jobThread as finished.
        jobProgressBar.setValue(100);
        showStatus(Status.DONE);
        jobIsFinished = true;

        for(Person myPerson : workingPeople) {
            myPerson.setAvailability(true);
            //SeaPortProgram.getResourceOutputArea().append("> " + jobThread.getName() + " thread just set" + myPerson + "to available.\n");
            //this.getResourceOutputArea().append("> " + jobThread.getName() + " thread just set" + myPerson.getName() + " to available.\n");
            myBuilder.append("> " + jobThread.getName() + " thread just set" + myPerson + "to available.\n");
        }

        //Synchronize on the SeaPort again and determine if a Ship has completed all of its Jobs
        //If so, notify the other Threads after the Ship leaves a Dock and a new one Docks
        synchronized (parentSeaPort) {
            if (shipsJobsFinished()) {
                shipLeaveDock();
            }
            parentSeaPort.notifyAll();
        }

        synchronized (this) {
            this.getResourceOutputArea().append(myBuilder.toString());
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    //Overload toString() with dummy parameter
    public String toString(int x) {
        return super.toString() +  " Job Requirements >> " + requirements;
    } //end of overloaded toString()

} //end of Job
