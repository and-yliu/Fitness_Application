package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.*;

import exception.InvalidInputException;
import model.Exercise;
import model.ExerciseCollection;
import model.ListOfExercise;

// Represent an Window that allows user to interact with their collection
public class CollectionGUI extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private DefaultListModel<Object> listModel;
    private JList<Object> list;
    private ExerciseCollection exerciseCollection;

    // EFFECTS: Instantiate the CollectionGUI frame
    public CollectionGUI(ExerciseCollection exerciseCollection) {
        super("Workout Planner");
        this.exerciseCollection = exerciseCollection;
        listModel = new DefaultListModel<>();
        list = new JList<Object>(listModel);
        setSize(WIDTH, HEIGHT);

        addActions();
        displayList(exerciseCollection, listModel, list, "Here is the Exercise Collection");
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Display the listOfExercise onto the frame with message string
    public void displayList(ListOfExercise listOfExercise, DefaultListModel<Object> model, JList<Object> list,
            String string) {
        for (Exercise e : listOfExercise.getExercises()) {
            model.addElement(e);
        }
        JLabel label = new JLabel(string);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(250, 100));
        add(label, BorderLayout.NORTH);
        add(listScroller, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: setting up the frame and enable actions
    public void addActions() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1));
        add(buttonPanel, BorderLayout.SOUTH);

        buttonPanel.add(new JButton(new DetailAction()));
        buttonPanel.add(new JButton(new AddExerciseAction()));
        buttonPanel.add(new JButton(new SortAction()));
        buttonPanel.add(new JButton(new QuitAction()));
    }

    // EFFECTS: print the details of the exercise to the terminal
    public void showExercisesDetail(Exercise exercise) {
        JFrame frame = new JFrame("Excercise Information");

        JLabel label = new JLabel("<html>Name: " + exercise.getName()
                + "<br>Description: " + exercise.getDescription() + "<br>Body: " + exercise.getBodyPart()
                + "<br>Duration: " + exercise.getDuration() + "</html>",
                JLabel.CENTER);
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.setSize(300, 200);

        frame.setVisible(true);
    }

    // EFFECTS: create a new frame to show the sorted list of exercise with given
    // input
    public void showSortedList(String input) {
        try {
            String bodyPart = whichBodyPart(input);
            JFrame frame = new JFrame("Sorted List of Excercise");
            DefaultListModel<Object> sortModel = new DefaultListModel<>();
            JList<Object> sortList = new JList<>(sortModel);
            JLabel label = new JLabel("Here is the sorted list for " + bodyPart + ":");

            for (Exercise e : exerciseCollection.getExercises()) {
                if (e.getBodyPart().equals(bodyPart)) {
                    sortModel.addElement(e);
                }
            }

            sortList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            sortList.setLayoutOrientation(JList.VERTICAL);
            sortList.setVisibleRowCount(-1);
            JScrollPane listScroller = new JScrollPane(sortList);
            frame.add(label, BorderLayout.NORTH);
            frame.add(listScroller, BorderLayout.CENTER);
            frame.setSize(300, 200);
            frame.setVisible(true);
        } catch (InvalidInputException e) {
            JOptionPane.showMessageDialog(null, "Invalid Input", "System Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // EFFECTS: convert the inputed str to a body part
    private String whichBodyPart(String str) throws InvalidInputException {
        String bodyPart = "";
        if (str.equalsIgnoreCase("a")) {
            bodyPart = "Abs";
        } else if (str.equalsIgnoreCase("ar")) {
            bodyPart = "Arm";
        } else if (str.equalsIgnoreCase("l")) {
            bodyPart = "Leg";
        } else {
            throw new InvalidInputException();
        }
        return bodyPart;
    }

    // Represent an action that shows the details of the selected exercise
    private class DetailAction extends AbstractAction {

        // EFFECTS: Instantiate detail action
        DetailAction() {
            super("Select Exercise to see Details");
        }

        // EFFECTS: show the detail of the selected exercise
        @Override
        public void actionPerformed(ActionEvent evt) {
            Exercise exercise = exerciseCollection.getExercises().get(list.getSelectedIndex());
            showExercisesDetail(exercise);
        }
    }

    // Represent an action that shows the details of the selected exercise
    private class AddExerciseAction extends AbstractAction {

        // EFFECTS: Instantiate add exercise action
        AddExerciseAction() {
            super("Add Exercise to Collection");
        }

        // MODIFIES: this
        // EFFECTS: ask the user an number of inputs and add a new exercise to
        // collection
        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                String name = JOptionPane.showInputDialog(null,
                        "Input the name of the exercise you want add:",
                        "Name of Exercise", JOptionPane.QUESTION_MESSAGE);

                String description = JOptionPane.showInputDialog(null,
                        "Input the description of workout:",
                        "Description of Exercise", JOptionPane.QUESTION_MESSAGE);

                String bodyPart = whichBodyPart(JOptionPane.showInputDialog(null,
                        "Which part of body does it exercise (a - Abs, ar - Arm, l - Leg):",
                        "Body Part of Exercise", JOptionPane.QUESTION_MESSAGE));

                String duration = JOptionPane.showInputDialog(null,
                        "Input the duration in seconds: ",
                        "Duration of Exercise", JOptionPane.QUESTION_MESSAGE);

                Exercise exercise = new Exercise(name, description, bodyPart, Integer.parseInt(duration));
                exerciseCollection.addExercise(exerciseCollection.getExercises().size(), exercise);

                listModel.addElement(exercise);
            } catch (InvalidInputException e) {
                JOptionPane.showMessageDialog(null, "Invalid Input", "System Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Represent a action that shows a sorted list of exercise with given bodyPart
    private class SortAction extends AbstractAction {

        // EFFECTS: Instantiate sort action
        SortAction() {
            super("Sort List by Body Part");
        }

        // EFFECTS: generate a sorted list by body part in a new frame
        @Override
        public void actionPerformed(ActionEvent evt) {
            String input = JOptionPane.showInputDialog(null,
                    "Which part of body does it exercise (a - Abs, ar - Arm, l - Leg):",
                    "Body Part of Exercise",
                    JOptionPane.QUESTION_MESSAGE);

            showSortedList(input);
        }
    }

    // Represent an Action that close the PlanGUI window
    private class QuitAction extends AbstractAction {

        // EFFECTS: Constructe an quit action
        QuitAction() {
            super("Quit Window");
        }

        // MODIFIES: this
        // EFFECTS: Close the window created by the CollectionGUI when clicked
        @Override
        public void actionPerformed(ActionEvent evt) {
            dispose();
        }
    }
}
