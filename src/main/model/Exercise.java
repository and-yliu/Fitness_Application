package model;

public class Exercise {
    private String name;
    private String description;
    private String bodyPart;
    private int duration;

    // EFFECT: create an exercise with name, description, body part, repetition and
    // duration
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

    // EFFECTS: print the details of the exercise to the terminal
    public boolean showExercises() {
        boolean complete = false;
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Body: " + bodyPart);
        System.out.println("Duration: " + duration + "\n");
        complete = true;

        return complete;
    }

}
