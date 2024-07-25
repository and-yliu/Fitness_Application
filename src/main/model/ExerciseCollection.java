package model;

import java.util.ArrayList;

//Represent a list of exercises that stores all the possible exercises
public class ExerciseCollection extends ListOfExercise {

    // EFFECTS: construct an exercise collection that store all possible exercise
    public ExerciseCollection() {
        super();
    }

    // MODIFIES: this
    // EFFECTS: add default exercise to the exercise collection
    public void addDefaultExercise() {
        ArrayList<Exercise> exercises = super.getExercises();
        exercises.add(new Exercise("Push-Ups", "On all fours and push up", "Arm", 60));
        exercises.add(new Exercise("Sit-Ups", "Lie down, bend knees, and lift torso up", "Abs", 60));
        exercises.add(new Exercise("Squats", "Stand feet apart and bend knees", "Leg", 60));
    }
}
