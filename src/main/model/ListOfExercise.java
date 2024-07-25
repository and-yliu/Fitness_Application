package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// A abstract class that represent a list of exercise 
public abstract class ListOfExercise implements Writable {
    private ArrayList<Exercise> listOfExercises;

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

    // EFFECTS: change the ListOfExerice object to JSONObject
    @Override
    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Exercises", exerciseToJson());
        return jsonObject;
    }

    // EFFECTS: change the list of exercise into an JSONArray and return it
    private JSONArray exerciseToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Exercise exercise : listOfExercises) {
            jsonArray.put(exercise.toJsonObject());
        }

        return jsonArray;
    }
}
