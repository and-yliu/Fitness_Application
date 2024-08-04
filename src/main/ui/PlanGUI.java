package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
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
import model.ListOfExercise;
import model.Plan;

public class PlanGUI extends JFrame{
    private JList<Object> collection;
    private DefaultListModel<Object> collectionModel;
    private ExerciseCollection exerciseCollection;
    private Plan plan;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    protected DefaultListModel<Object> listModel;
    protected JList<Object> list;
    private JPanel panel;

    public PlanGUI(Plan plan, ExerciseCollection exerciseCollection) {
        super("Workout Plan");
        this.exerciseCollection = exerciseCollection;
        this.plan = plan;
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        collectionModel = new DefaultListModel<>();
        listModel = new DefaultListModel<>();
        list = new JList<Object>(listModel);
        collection = new JList<Object>(collectionModel);
        setSize(WIDTH, HEIGHT);

        addActions();
        displayList(plan, listModel, list, "Here is your current workout plan: ");
        displayList(exerciseCollection, collectionModel, collection,"Here is the all the workouts in your collection: ");
        add(panel);
        setVisible(true);
    }

    public void displayList(ListOfExercise listOfExercise, DefaultListModel<Object> model, JList<Object> list, String string) {
        for (Exercise e : listOfExercise.getExercises()) {
            model.addElement(e);
        }
        JLabel label = new JLabel(string);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(250, 100));
        panel.add(label);
        panel.add(listScroller);
    }

    public void addActions() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1));
        add(buttonPanel, BorderLayout.SOUTH);

        buttonPanel.add(new JButton(new StartExerciseAction()));
        buttonPanel.add(new JButton(new RemoveExercisePlanAction()));
        buttonPanel.add(new JButton(new AddExercisePlanAction()));
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

        /** Returns an ImageIcon, or null if the path was invalid. */
    protected ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private class RemoveExercisePlanAction extends AbstractAction {
        RemoveExercisePlanAction() {
            super("Remove Seleted Exercise from Plan");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            Exercise exercise = plan.getExercises().get(list.getSelectedIndex());
            plan.removeExercise(plan.getExercises().indexOf(exercise));

            listModel.removeElement(exercise);
        }
    }

    private class AddExercisePlanAction extends AbstractAction {
        AddExercisePlanAction() {
            super("Add Seleted Exercise from Collection to Plan");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            Exercise exercise = exerciseCollection.getExercises().get(collection.getSelectedIndex());
            plan.addExercise(plan.getExercises().size(), exercise);
            listModel.addElement(exercise);
        }
    }

    private class StartExerciseAction extends AbstractAction {

        StartExerciseAction() {
            super("Start Workout");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            BufferedImage myPicture;
            try {
                myPicture = ImageIO.read(new File("./image/workout.jpg"));
                Image picture = myPicture.getScaledInstance(600,400, myPicture.SCALE_DEFAULT);
                JLabel image = new JLabel(new ImageIcon(picture));
                JFrame frame = new JFrame("Start Workout");
                frame.add(image);
                frame.setSize(600, 400);
                frame.setVisible(true);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error", "System Error",
                        JOptionPane.ERROR_MESSAGE);
            }
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

