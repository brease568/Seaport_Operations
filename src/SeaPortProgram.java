/**
 * File: SeaPortProgram.java
 * Date: August 26, 2019
 * @Author: Brian Rease
 * Purpose: This program is meant to simulate some of the aspects of a number of Sea Ports. This is the main class of the program.
 * It initializes the program and creates the GUI. It creates a private instance of the 'World' class which is built from the data file
 * selected by the user.

 * This specific class implements the GUI for the program and the main() method.
 */

package com.brianrease;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.File;

public class SeaPortProgram extends JFrame {
    static final long serialVersionUID = 123L;

    //create all objects for the main GUI
    private JButton chooseFileButton = new JButton("Choose File"),
            generateOutputButton = new JButton("Read File"),
            searchButton = new JButton("Search Data"),
            sortButton = new JButton("Sort Data");
    private JTextField dataFileField = new JTextField(10),
            searchCriteriaField = new JTextField(10);
    private JLabel searchCriteriaDropDownLabel = new JLabel("Select Search Criteria :", SwingConstants.CENTER),
            searchCriteriaLabel = new JLabel("Enter Search Criteria :", SwingConstants.CENTER),
            dataFileLabel = new JLabel("File About to Read >>>", SwingConstants.CENTER),
            sortAttributeLabel = new JLabel("Sort by Attribute:", SwingConstants.CENTER),
            sortByThingLabel = new JLabel("Sort by Thing:", SwingConstants.CENTER);
    private JTextArea dataOutputArea = new JTextArea(25, 50);
    private JScrollPane dataScrollPane = new JScrollPane();

    private String[] searchCriteriaStrings = { "", "By Name", "By Index", "By Skill"};
    private String[] sortAttributeStrings = {"Name", "Weight", "Length", "Width", "Draft"};
    private String[] sortCriteriaThing = {"SeaPort", "Dock", "Ship", "Job", "Person"};
    private JComboBox<String> searchCriteriaBox = new JComboBox<>(searchCriteriaStrings);
    private JComboBox<String> sortAttributeBox = new JComboBox<>(sortAttributeStrings);
    private JComboBox<String> sortCriteriaThingBox = new JComboBox<>(sortCriteriaThing);

    //objects used for the JTree displaying the World
    private JPanel myTreePanel;
    private JTree myTree;

    //File to read data from
    private File selectedFile;

    //variables for Project1
    private World world;

    //Adding objects for Project 3 Job GUI elements
    private static JPanel myJobPanel;

    //Adding to log resource pool
    private static JTextArea resourceOutputArea = new JTextArea(5,5);
    private JScrollPane resourceScrollPane = new JScrollPane();

    //create constructor for the GUI
    public SeaPortProgram() {
        super("SeaPort Program");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        //create northPanel to put on the BorderLayout.NORTH
        JPanel northPanel = new JPanel();

        GridLayout northLayout = new GridLayout(1,3,10,30);
        northPanel.setLayout(northLayout);

        JPanel northLeftPanel = new JPanel();
        GridLayout northLeftLayout = new GridLayout(2,3,5,5);
        northLeftPanel.setLayout(northLeftLayout);

        //add to top left
        northPanel.add(northLeftPanel);
        //add to north left panel
        northLeftPanel.add(chooseFileButton);
        northLeftPanel.add(dataFileLabel);
        dataFileField.setEditable(false);
        northLeftPanel.add(dataFileField);
        northLeftPanel.add(generateOutputButton);
        northLeftPanel.add(new JLabel(""));
        northLeftPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2), BorderFactory.createEmptyBorder(10,10,10,10)));

        JPanel northCenterPanel = new JPanel();
        GridLayout northCenterLayout = new GridLayout(3,2, 5,5);
        northCenterPanel.setLayout(northCenterLayout);

        //add to top center
        northPanel.add(northCenterPanel);
        northCenterPanel.add(searchCriteriaDropDownLabel);
        northCenterPanel.add(searchCriteriaBox);
        northCenterPanel.add(searchCriteriaLabel);
        northCenterPanel.add(searchCriteriaField);
        northCenterPanel.add(searchButton);
        northCenterPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2), BorderFactory.createEmptyBorder(10,10,10,10)));


        JPanel northRightPanel = new JPanel();
        GridLayout northRightLayout = new GridLayout(3,2,5,5);
        northRightPanel.setLayout(northRightLayout);

        //add to top right
        northPanel.add(northRightPanel);
        northRightPanel.add(sortByThingLabel);
        northRightPanel.add(sortCriteriaThingBox);
        northRightPanel.add(sortAttributeLabel);
        northRightPanel.add(sortAttributeBox);
        northRightPanel.add(sortButton);
        northRightPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2), BorderFactory.createEmptyBorder(10,10,10,10)));

        //add the northPanel which has all of the buttons, labels, comboBox, fields, etc.
        add(northPanel, BorderLayout.NORTH);

        //configure the scrollPane and textArea
        dataScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        dataScrollPane.getViewport().add(dataOutputArea);
        dataOutputArea.setBorder(new LineBorder(Color.BLACK));
        dataOutputArea.setFont(new java.awt.Font("Monospaced", 0, 12));
        dataOutputArea.setEditable(false);
        dataOutputArea.setSize(new Dimension(500, 500));

        //add the scrollPane with the textArea attached
        add(dataScrollPane, BorderLayout.CENTER);

        //create the JPanel for the left-hand side of the GUI
        myTreePanel = new JPanel(new BorderLayout());
        myTreePanel.setPreferredSize(new Dimension(300, 500));
        myTreePanel.setBorder(new EmptyBorder(10, 20, 30, 40));
        add(myTreePanel, BorderLayout.WEST);

        //Adding code for Project 3 Job info
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BorderLayout());
        myJobPanel = new JPanel();
        myJobPanel.setLayout(new GridLayout(0, 5, 5, 5));
        myJobPanel.setSize(new Dimension(300, 500));
        JScrollPane jobScrollPane = new JScrollPane();
        jobScrollPane.getViewport().add(myJobPanel);
        eastPanel.add(jobScrollPane, BorderLayout.CENTER);
        add(eastPanel, BorderLayout.EAST);

        //Adding code for Project 4 Resource Log
        resourceScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        resourceScrollPane.getViewport().add(resourceOutputArea);
        //resourceOutputArea.setSize(new Dimension(500, 700));
        resourceScrollPane.setBorder(new EmptyBorder(20, 50, 50, 20));
        //resourceScrollPane.setSize(new Dimension(500, 500));
        resourceOutputArea.setFont(new java.awt.Font("Monospaced", 0, 12));
        resourceOutputArea.setBorder(new TitledBorder("Resource Pool (Workers)"));
        resourceOutputArea.setEditable(false);
        JPanel southPanel = new JPanel();
        BorderLayout southLayout = new BorderLayout();
        southPanel.setLayout(southLayout);
        add(southPanel, BorderLayout.SOUTH);
        southPanel.add(resourceScrollPane, BorderLayout.CENTER);

        //Set the size of the main GUI
        setSize(1600, 700);

        //create an actionListener for each button
        chooseFileButton.addActionListener(event -> processChooseFileButton());
        generateOutputButton.addActionListener(event -> processGenerateOutputButton());
        searchButton.addActionListener(event -> processSearchButton());
        sortButton.addActionListener(event -> processSortButton());
    } //end of constructor

    //this method is called via an actionListener when a user clicks the chooseFileButton
    private void processChooseFileButton() {
        try {
            dataOutputArea.setText("");
            JFileChooser myFileChooser = new JFileChooser(".", FileSystemView.getFileSystemView());
            int returnValue = myFileChooser.showOpenDialog(null);
            if(returnValue == JFileChooser.APPROVE_OPTION) {
                selectedFile = myFileChooser.getSelectedFile();
                dataFileField.setText(selectedFile.getName());
            }
        } catch(Exception e) {
            System.out.println("There was an error with JFileChooser!\n\n" + e.getMessage());
        } //end of catch()
    } //end of processChooseFileButton()

    //this method is called via an actionListener when a user clicks the generateOutputButton
    private void processGenerateOutputButton() {
        try {
            if(selectedFile != null) {
                //initialScannerOfDataFile();
                world = new World("My World", 0, 0, selectedFile);
                String myTotalOutput = world.toString();
                dataOutputArea.setText(myTotalOutput);
                createTree();

            } else {
                JOptionPane.showMessageDialog(null, "No selected file!");
            }
        } catch(Exception e2) {
            System.out.println("Something is wrong with output generation!\n\n" + e2.getMessage());
        } //end of catch()
    } //end processGenerateOutputButton()

    //this method is called via an actionListener when a user clicks the searchButton
    private void processSearchButton() {
        try {
            //Stop a user from searching before a World exists to search
            if(this.world == null) {
                JOptionPane.showMessageDialog(null, "You must read a data file first!", "Error", JOptionPane.ERROR_MESSAGE);
            }

            //Declarations
            StringBuilder searchResults;
            String searchCriteria;
            String unfoundItem;
            int dropDownSelection;

            //initialize
            searchResults = new StringBuilder();
            searchCriteria = this.searchCriteriaField.getText().trim().toLowerCase();
            unfoundItem = this.searchCriteriaField.getText().trim().toLowerCase();
            dropDownSelection = this.searchCriteriaBox.getSelectedIndex();

            //Stop users from searching before selecting and entering any search criteria
            if(searchCriteria.equals("") || searchCriteriaBox.equals(0)) {
                JOptionPane.showMessageDialog(null, "You must SELECT and ENTER the search criteria before searching!", "Error", JOptionPane.ERROR_MESSAGE);
            }

            //handle each dropdown selection
            switch(dropDownSelection) {
                case 0: //empty string to allow a blank default
                    break;
                case 1: //by name
                    for(Thing myThing : this.world.getAllThings()) {
                        if (myThing.getName().toLowerCase().equals(searchCriteria)) {
                            searchResults = new StringBuilder(myThing.getName() + " " + myThing.getIndex() + " (" + myThing.getClass().getSimpleName() + ")");
                        }
                    }    //end of forLoop for case 1
                    if(searchResults.toString().equals("")) {
                        JOptionPane.showMessageDialog(null, "Search criteria '" + unfoundItem + "' does not exist in this World.", "Unknown Item", JOptionPane.WARNING_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, searchResults.toString());
                    }
                    break;
                case 2: //by index
                    for(Thing myThing : this.world.getAllThings()) {
                        if (myThing.getIndex() == Integer.parseInt(searchCriteria)) {
                            searchResults = new StringBuilder(myThing.getName() + " " + myThing.getIndex() + " (" + myThing.getClass().getSimpleName() + ")");
                        }
                    } //end of forLoop for case 2
                    if(searchResults.toString().equals("")) {
                        JOptionPane.showMessageDialog(null, "Search criteria '" + unfoundItem + "' does not exist in this World.", "Unknown Item", JOptionPane.WARNING_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, searchResults.toString());
                    }
                    break;
                case 3: //by skill
                    for(SeaPort mySeaPort : this.world.getPorts()) {
                        for(Person myPerson : mySeaPort.getPersons()) {
                            if(myPerson.getSkill().toLowerCase().equals(searchCriteria)) {
                                searchResults.append(myPerson.getName()).append(" ").append(myPerson.getIndex()).append(" (").append(myPerson.getClass().getSimpleName()).append(")\n Skill: ").append(myPerson.getSkill()).append("\n\n");
                            }
                        }
                    } //end of forLoop for case 3
                    if(searchResults.toString().equals("")) {
                        JOptionPane.showMessageDialog(null, "Search criteria '" + unfoundItem + "' does not exist in this World.", "Unknown Item", JOptionPane.WARNING_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, searchResults.toString());
                    }
                    break;
                default: break;
            } //end of switch()
        } catch(Exception e5) {
            JOptionPane.showMessageDialog(null, "Something went wrong in the search!\n\n" + e5.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } //end of catch()
    } //end of processSearchButton()

    //This method is called via an actionListener when a user clicks the sortButton
    private void processSortButton() {
        try {
            dataOutputArea.setText("");

            //Stop a user from sorting before a World exists to search
            if(this.world == null) {
                JOptionPane.showMessageDialog(null, "You must read a data file first!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //First if stops a user from attempting to sort any type of thing other than a Ship by any attribute other than Name
            if (!sortCriteriaThingBox.getSelectedItem().toString().toLowerCase().equals("ship") && !sortAttributeBox.getSelectedItem().toString().toLowerCase().equals("name")) {
                JOptionPane.showMessageDialog(null, "You can only sort Ships by an attribute other than 'Name'!", "Error", JOptionPane.WARNING_MESSAGE);
                //This if will sort whatever Thing is selected by calling sortMyThingByName in the World class
            } else if (sortAttributeBox.getSelectedItem().toString().toLowerCase().equals("name")) {
                String sortThing = sortCriteriaThingBox.getSelectedItem().toString();
                String sortResultByName = world.sortMyThingByName(sortThing);
                dataOutputArea.setText(sortResultByName);
            } else {
                String sortAttribute = sortAttributeBox.getSelectedItem().toString();
                String sortResultByAttribute = world.sortShipByAttribute(sortAttribute);
                dataOutputArea.setText(sortResultByAttribute);
            }
        } catch(Exception e7) {
            JOptionPane.showMessageDialog(null, "Something went wrong while sorting!\n\n" + e7.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } //end of catch()
    } //end of processSortButton()

    //This method is called once a user reads a data file. It creates a JTree to put on the Tree Panel
    //that displays the contents of the created World in a tree structure.
    private void createTree() {
        DefaultMutableTreeNode topNode = new DefaultMutableTreeNode("The World");
        myTree = new JTree(topNode);

        DefaultMutableTreeNode mainPortNode = new DefaultMutableTreeNode("Sea Ports");
        topNode.add(mainPortNode);

        for(SeaPort mySeaPort : world.getPorts()) {
            DefaultMutableTreeNode individualPortNode = new DefaultMutableTreeNode(mySeaPort.getName());
            mainPortNode.add(individualPortNode);

            DefaultMutableTreeNode peopleNode = new DefaultMutableTreeNode("People");
            individualPortNode.add(peopleNode);
            for(Person myPerson : mySeaPort.getPersons()) {
                DefaultMutableTreeNode individualPeopleNode = new DefaultMutableTreeNode(myPerson.getName());
                peopleNode.add(individualPeopleNode);
            }

            DefaultMutableTreeNode dockNode = new DefaultMutableTreeNode("Docks");
            individualPortNode.add(dockNode);
            for(Dock myDock : mySeaPort.getDocks()) {
                DefaultMutableTreeNode individualDockNode = new DefaultMutableTreeNode(myDock.getName());
                dockNode.add(individualDockNode);
                if(myDock.getShip() != null) {
                    DefaultMutableTreeNode dockedShip = new DefaultMutableTreeNode(myDock.getShip().getName());
                    individualDockNode.add(dockedShip);
                    for(Job myJob : myDock.getShip().getJobs()) {
                        DefaultMutableTreeNode dockedShipJob = new DefaultMutableTreeNode(myJob.getName());
                        dockedShip.add(dockedShipJob);
                    }
                }
            }

            DefaultMutableTreeNode portQueNode = new DefaultMutableTreeNode("Ship Queue");
            individualPortNode.add(portQueNode);
            for(Ship myShip : mySeaPort.getQue()) {
                DefaultMutableTreeNode quedShip = new DefaultMutableTreeNode(myShip.getName());
                portQueNode.add(quedShip);
                for(Job myJob : myShip.getJobs()) {
                    DefaultMutableTreeNode quedShipJob = new DefaultMutableTreeNode(myJob.getName());
                    quedShip.add(quedShipJob);
                }
            }

        } //end of initial for loop inside createTree() method

        /*This method call is added in case a user reads a new data file after they
        have already read one or more data files.
         */
        myTreePanel.removeAll();

        //Add everything to the myTreePanel
        JScrollPane myTreeScroll = new JScrollPane(myTree);
        myTreePanel.add(myTreeScroll, BorderLayout.CENTER);
        validate();
    } //end of createTree()

    //This method was created to serve a JPanel to the Job class when
    //a Job is being created from the data file.
    public static JPanel getJobPanel() {
        return myJobPanel;
    }

    //This method is called from the Job class in order to be update the GUI for the resource pool.
    public synchronized static JTextArea getResourceOutputArea() {
        return resourceOutputArea;
    }

    public static void main(String[] args) {
        SeaPortProgram window = new SeaPortProgram();
        window.setVisible(true);
    } //end of main()
} //end of SeaPortProgram
