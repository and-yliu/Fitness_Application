package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import model.Exercise;
import model.ExerciseCollection;
import model.ListOfExercise;
import model.Plan;

public abstract class ListGUI extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    protected ListOfExercise listOfExercise;
    protected DefaultListModel<Object> listModel;
    protected JList<Object> list;

    public ListGUI(ListOfExercise listOfExercise, String name, String message) {
        super("Workout Planner");
        this.listOfExercise = listOfExercise;
        listModel = new DefaultListModel<>();
        setSize(WIDTH, HEIGHT);

        addActions();
        displayList(message);
        setVisible(true);
    }

    public void displayList(String string) {
        for (Exercise e : listOfExercise.getExercises()) {
            listModel.addElement(e);
        }

        JLabel label = new JLabel(string);
        list = new JList<Object>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(250, 200));
        add(label, BorderLayout.NORTH);
        add(listScroller, BorderLayout.CENTER);
    }

    public abstract void addActions();
}    
