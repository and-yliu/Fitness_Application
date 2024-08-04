package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

import exception.InvalidInputException;
import model.Exercise;
import model.ExerciseCollection;

public class CollectionGUI extends ListGUI {
    ExerciseCollection exerciseCollection;

    public CollectionGUI(ExerciseCollection exerciseCollection) {
        super(exerciseCollection ,"Exercise Collection", "Exercise Collection");
        this.exerciseCollection = exerciseCollection;
    }


    @Override
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

    public void showSortedList(String input){
        try {
            String bodyPart = whichBodyPart(input);
            JFrame frame = new JFrame("Sorted List of Excercise");
            DefaultListModel<Object> sortModel = new DefaultListModel<>();
            JList<Object> sortList = new JList<>(sortModel);
            JLabel label = new JLabel("Here is the sorted list for " + bodyPart + ":");

            for(Exercise e: exerciseCollection.getExercises()){
                if(e.getBodyPart().equals(bodyPart)){
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

    private class DetailAction extends AbstractAction {

        DetailAction() {
            super("Select Exercise to see Details");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            Exercise exercise = listOfExercise.getExercises().get(list.getSelectedIndex());
            showExercisesDetail(exercise);
        }
    }

    private class AddExerciseAction extends AbstractAction {
        AddExerciseAction() {
            super("Add Exercise to Collection");
        }

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
                listOfExercise.addExercise(listOfExercise.getExercises().size(), exercise);

                listModel.addElement(exercise);
            } catch (InvalidInputException e) {
                JOptionPane.showMessageDialog(null, "Invalid Input", "System Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class SortAction extends AbstractAction {

        SortAction() {
            super("Sort List by Body Part");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            String input = JOptionPane.showInputDialog(null,
                    "Which part of body does it exercise (a - Abs, ar - Arm, l - Leg):",
                    "Body Part of Exercise",
                    JOptionPane.QUESTION_MESSAGE);

            showSortedList(input);
        }
    }

    private class QuitAction extends AbstractAction {
        QuitAction() {
            super("Quit Window");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            dispose();
        }
    }
}
