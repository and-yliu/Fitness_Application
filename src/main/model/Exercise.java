package model;

import org.json.JSONObject;

import persistence.Writable;

//Represent an exerise that has a name description, body part that it exercises, and duration
public class Exercise implements Writable {
    private String name;
    private String description;
    private String bodyPart;
    private int duration;

    // EFFECT: create an exercise with name, description, body part, duration
    public Exercise(String name, String description, String bodyPart, int duration) {
        this.name = name;
        this.description = description;
        this.bodyPart = bodyPart;
        this.duration = duration;
    }

    // EFFECTS: get the description of the exercise
    public String getName() {
        return name;
    }

    // EFFECTS: get the description of the exercise
    public String getDescription() {
        return description;
    }

    // EFFECTS: get the bodyPart that is exercised by the exercise
    public String getBodyPart() {
        return bodyPart;
    }

    // EFFECTS: get the duration of the exercise
    public int getDuration() {
        return duration;
    }

    // REQUIRES: duration > 0
    // MODIFIES: this
    // EFFECTS: change the duration of the exercise
    public void setDuration(int duration) {
        this.duration = duration;
    }

    // EFFECTS: change Exercise object to JSONObject
    @Override
    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("name", name);
        jsonObject.put("description", description);
        jsonObject.put("bodyPart", bodyPart);
        jsonObject.put("duration", duration);

        return jsonObject;
    }

    @Override
    public String toString() {
        return name + ": " + duration + " sec";
    }
    
}