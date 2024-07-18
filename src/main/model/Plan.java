package model;

import java.util.ArrayList;

//Represent a plan of exercise you are planing to do 
public class Plan extends ListOfExercise {
    private ArrayList<Exercise> completedExercise;

    // EFFECT: create a empty plan of exercises and an empty completed list
    public Plan() {
        super();
        completedExercise = new ArrayList<>();
    }

    // REQUIRES: index > 0;
    // MODIFIES: this
    // EFFECTS: add a rest to a given index in the list of exercises;
    public void addRest(int index) {
        Exercise rest = new Exercise("Rest", null, null, 30);
        listOfExercises.add(index, rest);
    }

    // EFFECTS: return the total number of seconds the whole plan is going to take
    public int getTotalDuration() {
        int time = 0;

        for (Exercise e : listOfExercises) {
            time += e.getDuration();
        }
        return time;
    }
    
    // MODIFIES: this
    // EFFECTS: Remove the given exercise from the list of ongoing exercise
    // and add it to the list of completed exercise
    public void completeExercise(Exercise exercise) {
        listOfExercises.remove(exercise);
        completedExercise.add(exercise);
    }

    // EFFECTS: return the list of completed exercise
    public ArrayList<Exercise> getCompletedExercise() {
        return completedExercise;
    }
}
