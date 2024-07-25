package persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import model.Exercise;
import model.ListOfExercise;
import model.Plan;

public class TestJsonWriter {
    private ListOfExercise listOfExercise;
    private JsonWriter jsonWriter;

    @Test
    void testInvalidFile() {
        try {
            listOfExercise = new Plan();
            jsonWriter = new JsonWriter("./data/my\0illegal:fileName.json");
            jsonWriter.open();
            fail("IOException did not appear");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testSaveEmptyList() {
        try {
            listOfExercise = new Plan();
            jsonWriter = new JsonWriter("./data/testWrite.json");
            jsonWriter.open();
            jsonWriter.write(listOfExercise);
            jsonWriter.close();

            JsonReader jsonReader = new JsonReader("./data/testWrite.json");
            listOfExercise = jsonReader.readPlan();
            assertEquals(listOfExercise.getExercises().size(), 0);
        } catch (Exception e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    void testSaveSampleList() {
        try {
            listOfExercise = new Plan();
            jsonWriter = new JsonWriter("./data/testWrite.json");

            Exercise exercise1 = new Exercise("Push-Ups", "Push-Ups", "Arm", 60);
            Exercise exercise2 = new Exercise("Sit-Ups", "Sit-Ups", "Abs", 60);
            Exercise exercise3 = new Exercise("Squats", "Squats", "Leg", 60);
            listOfExercise.addExercise(0, exercise1);
            listOfExercise.addExercise(1, exercise2);
            listOfExercise.addExercise(2, exercise3);

            jsonWriter.open();
            jsonWriter.write(listOfExercise);
            jsonWriter.close();

            JsonReader jsonReader = new JsonReader("./data/testWrite.json");
            listOfExercise = jsonReader.readPlan();
            assertEquals(listOfExercise.getExercises().size(), 3);
            assertEquals(listOfExercise.getExercises().get(0).getName(), "Push-Ups");
            assertEquals(listOfExercise.getExercises().get(1).getName(), "Sit-Ups");
            assertEquals(listOfExercise.getExercises().get(2).getName(), "Squats");

        } catch (Exception e) {
            fail("Exception should not be thrown");
        }
    }

}
