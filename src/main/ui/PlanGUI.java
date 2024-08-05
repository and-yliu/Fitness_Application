package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import model.Exercise;
import model.ExerciseCollection;
import model.ListOfExercise;
import model.Plan;

// Represent an Window that allows user to interact with their plan
public class PlanGUI extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private JList<Object> collection;
    private DefaultListModel<Object> collectionModel;
    private ExerciseCollection exerciseCollection;
    private Plan plan;
    protected DefaultListModel<Object> listModel;
    protected JList<Object> list;
    private JPanel panel;

    // EFFECTS: Instantiate the PlanGUI frame
    public PlanGUI(Plan plan, ExerciseCollection exerciseCollection) {
        super("Workout Plan");
        this.exerciseCollection = exerciseCollection;
        this.plan = plan;
        startFrame();
    }

    // MODIFIES: this
    // EFFECTS: setting up the frame and enable actions
    private void startFrame() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        collectionModel = new DefaultListModel<>();
        listModel = new DefaultListModel<>();
        list = new JList<Object>(listModel);
        collection = new JList<Object>(collectionModel);
        setSize(WIDTH, HEIGHT);

        addActions();
        displayList(plan, listModel, list, "Here is your current workout plan: ");
        displayList(exerciseCollection, collectionModel, collection,
                "Here is the all the workouts in your collection: ");
        add(panel);
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
        panel.add(label);
        panel.add(listScroller);
    }

    // MODIFIES: this
    // EFFECTS: Add a panel of buttons to the frame
    public void addActions() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1));
        add(buttonPanel, BorderLayout.SOUTH);

        buttonPanel.add(new JButton(new StartExerciseAction()));
        buttonPanel.add(new JButton(new RemoveExercisePlanAction()));
        buttonPanel.add(new JButton(new AddExercisePlanAction()));
        buttonPanel.add(new JButton(new QuitAction()));
    }

    // Represent an Action that remove an exercise to the plan
    private class RemoveExercisePlanAction extends AbstractAction {

        // EFFECTS: Instantiate the add exercise plan action
        RemoveExercisePlanAction() {
            super("Remove Seleted Exercise from Plan");
        }

        // MODIFIES: this
        // EFFECTS: remove an exercise from the plan when clicked
        @Override
        public void actionPerformed(ActionEvent evt) {
            Exercise exercise = plan.getExercises().get(list.getSelectedIndex());
            plan.removeExercise(plan.getExercises().indexOf(exercise));

            listModel.removeElement(exercise);
        }
    }

    // Represent an Action that add an exercise to the plan
    private class AddExercisePlanAction extends AbstractAction {

        // EFFECTS: Instantiate the add exercise plan action
        AddExercisePlanAction() {
            super("Add Seleted Exercise from Collection to Plan");
        }

        // MODIFIES: this
        // EFFECTS: add an exercise from exerciseCollection to plan when clicked
        @Override
        public void actionPerformed(ActionEvent evt) {
            Exercise exercise = exerciseCollection.getExercises().get(collection.getSelectedIndex());
            plan.addExercise(plan.getExercises().size(), exercise);
            listModel.addElement(exercise);
        }
    }

    // Represent an Action that display an image that indicate the start of a
    // workout
    private class StartExerciseAction extends AbstractAction {

        // EFFECTS: Instantiate of the start exercise action
        StartExerciseAction() {
            super("Start Workout");
        }

        // EFFECTS: Create a new window with only the picture in it when clicked
        @Override
        public void actionPerformed(ActionEvent evt) {
            BufferedImage myPicture;
            try {
                myPicture = ImageIO.read(new File("./image/workout.jpg"));
                Image resizedPicture = myPicture.getScaledInstance(600, 400, Image.SCALE_DEFAULT);
                JLabel label = new JLabel(new ImageIcon(resizedPicture));
                JFrame frame = new JFrame("Start Workout");
                frame.add(label);
                frame.setSize(600, 400);
                frame.setVisible(true);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error", "System Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Represent an Action that close the PlanGUI window
    private class QuitAction extends AbstractAction {

        // EFFECTS: Constructe an quit action
        QuitAction() {
            super("Quit Window");
        }

        // MODIFIES: this
        // EFFECTS: Close the window created by the PlanGUI when clicked
        @Override
        public void actionPerformed(ActionEvent evt) {
            dispose();
        }
    }
}
