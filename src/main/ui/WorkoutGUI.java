package ui;

import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;

import model.Event;
import model.EventLog;
import model.ExerciseCollection;
import model.Plan;
import persistence.JsonReader;
import persistence.JsonWriter;

// Represent the main menu of the Workout App
// With references to https://docs.oracle.com/javase/tutorial/uiswing/components/list.html
// Alarm System: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git
// SimpleDrawingEditor: https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Starter.git
public class WorkoutGUI extends JFrame implements WindowListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final String JSON_FILE_PLAN = "./data/plan.json";
    private static final String JSON_FILE_COLLECTION = "./data/collection.json";
    protected ExerciseCollection exerciseCollection;
    protected Plan plan;
    private JsonWriter jsonWriterPlan;
    private JsonReader jsonReaderPlan;
    private JsonWriter jsonWriterCol;
    private JsonReader jsonReaderCol;
    private JLabel label;

    // EFFECTS: instantiate the WorkoutGUI frame
    public WorkoutGUI() {
        super("Workout Planner");
        setSize(WIDTH, HEIGHT);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initializeApp();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: setting up the frame and enable actions
    private void initializeApp() {
        jsonWriterPlan = new JsonWriter(JSON_FILE_PLAN);
        jsonReaderPlan = new JsonReader(JSON_FILE_PLAN);
        jsonWriterCol = new JsonWriter(JSON_FILE_COLLECTION);
        jsonReaderCol = new JsonReader(JSON_FILE_COLLECTION);
        exerciseCollection = new ExerciseCollection();
        label = new JLabel("Welcome to Workout Planner!", SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.PLAIN, 30));
        add(label, BorderLayout.CENTER);
        plan = new Plan();
        exerciseCollection.addDefaultExercise();
        plan.addRest(0);
        addButtonPanel();
        addWindowListener(this);
    }

    // EFFECTS: print the event log to console
    public void printLog(EventLog eventLog) {
        for (Event e : eventLog) {
            System.out.println(e.toString() + "\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: add a panel of buttons to the frame
    private void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1));
        add(buttonPanel, BorderLayout.SOUTH);

        buttonPanel.add(new JButton(new PlanAction()));
        buttonPanel.add(new JButton(new CollectionAction()));
        buttonPanel.add(new JButton(new SaveAction()));
        buttonPanel.add(new JButton(new LoadAction()));
        buttonPanel.add(new JButton(new QuitAction()));
    }

    // Represent an action that opens the PlanGUI
    private class PlanAction extends AbstractAction {

        // EFFECT: Instantiate plan action
        PlanAction() {
            super("Plan");
        }

        // EFFECT: Open a new window in PlanGUI
        @Override
        public void actionPerformed(ActionEvent evt) {
            new PlanGUI(plan, exerciseCollection);
        }
    }

    // Represent an action that opens the CollectionGUI
    private class CollectionAction extends AbstractAction {

        // EFFECT: Instantiate collection action
        CollectionAction() {
            super("Exercise Collection");
        }

        // EFFECT: Open a new window in CollectionGUI
        @Override
        public void actionPerformed(ActionEvent evt) {
            new CollectionGUI(exerciseCollection);
        }
    }

    // Represent an action that save the current state of the app
    private class SaveAction extends AbstractAction {

        // EFFECT: Instantiate save action
        SaveAction() {
            super("Save Plan");
        }

        // MODIFIES: this
        // EFFECTS: save the app to the file
        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                jsonWriterPlan.open();

                jsonWriterPlan.write(plan);
                jsonWriterPlan.close();

                jsonWriterCol.open();
                jsonWriterCol.write(exerciseCollection);
                jsonWriterCol.close();

                JOptionPane.showMessageDialog(null, "Saved plan to file!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Unable to write to file!", "System Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Represent an action that load the saved state of the app
    private class LoadAction extends AbstractAction {

        // EFFECT: Instantiate load action
        LoadAction() {
            super("Load Plan");
        }

        // MODIFIES: this
        // EFFECTS: load the saved app state from the file
        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                plan = jsonReaderPlan.readPlan();
                exerciseCollection = jsonReaderCol.readCollection();
                JOptionPane.showMessageDialog(null, "Loaded saved plan from file!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Unable to read from file!", "System Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Represent an Action that close the WorkoutGUI window
    private class QuitAction extends AbstractAction {

        // EFFECTS: Constructe an quit action
        QuitAction() {
            super("Quit Application");
        }

        // MODIFIES: this
        // EFFECTS: Close the window when clicked and exit program
        @Override
        public void actionPerformed(ActionEvent evt) {
            dispose();
        }
    }

    // EFFECTS: print eventlog when window is closed
    @Override
    public void windowClosed(WindowEvent e) {
        printLog(EventLog.getInstance());
    }

    @Override
    public void windowOpened(WindowEvent e) {
        // Method Not Called
    }

    @Override
    public void windowIconified(WindowEvent e) {
        // Method Not Called
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // Method Not Called
    }

    @Override
    public void windowActivated(WindowEvent e) {
        // Method Not Called
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // Method Not Called
    }

    @Override
    public void windowClosing(WindowEvent e) {
        // Method Not Called
    }
}
