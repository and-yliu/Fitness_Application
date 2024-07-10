package model;

public class ExerciseCollection extends ListOfExercise {

    // EFFECTS: creates a exercise database that store all possible exercise
    public ExerciseCollection() {
        super();
        listOfExercises.add(new Exercise("Push-Ups", "On all fours and push up", "Arm", 60));
        listOfExercises.add(new Exercise("Sit-Ups", "Lie down, bend knees, and lift torso up", "Abs", 60));
        listOfExercises.add(new Exercise("Squats", "Stand feet apart and bend knees", "Leg", 60));
    }
}
