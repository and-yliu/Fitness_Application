package model;

import java.util.ArrayList;

public abstract class ListOfExercise {
    protected ArrayList<Exercise> listOfExercises;

    // EFFECTS: create an empty list of exercises
    public ListOfExercise() {
        listOfExercises = new ArrayList<>();
    }

    // REQUIRES: index >= 0
    // MODIFIES: this
    // EFFECTS: add an exercise to the plan to the given index
    public void addExercise(int index, Exercise exercise) {
        listOfExercises.add(index, exercise);
    }

    // REQUIRE: index >= 0
    // MODIFIES: this
    // EFFECTS: remove an exercise from the plan at given index
    public void removeExercise(int index) {
        listOfExercises.remove(index);
    }

    // EFFECTS: return the listOfExercise
    public ArrayList<Exercise> getExercises() {
        return listOfExercises;
    }
}
