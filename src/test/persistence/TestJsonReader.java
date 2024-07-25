package persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import model.ExerciseCollection;
import model.Plan;

public class TestJsonReader {
    private ExerciseCollection exerciseCollection;
    private Plan plan;

    @Test
    void testReadInvalidFile(){
        try {
            JsonReader jsonReader = new JsonReader("./data/invalidFile.json");
            exerciseCollection = jsonReader.readCollection();
            fail("Exception should be thrown");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReadEmptyFile(){
        try {
            JsonReader jsonReader = new JsonReader("./data/testEmpty.json");
            exerciseCollection = jsonReader.readCollection();
            assertEquals(exerciseCollection.getExercises().size(), 0);
            
            plan = jsonReader.readPlan();
            assertEquals(plan.getExercises().size(), 0);
        } catch (IOException e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    void testReadGeneralFile(){
        try {
            JsonReader jsonReaderCol = new JsonReader("./data/testReadCollection.json");
            JsonReader jsonReaderPlan = new JsonReader("./data/testReadPlan.json");

            exerciseCollection = jsonReaderCol.readCollection();
            assertEquals(exerciseCollection.getExercises().size(), 4);
            assertEquals(exerciseCollection.getExercises().get(0).getName(), "Jump");
            assertEquals(exerciseCollection.getExercises().get(1).getName(), "Push-Ups");
            assertEquals(exerciseCollection.getExercises().get(2).getName(), "Sit-Ups");
            assertEquals(exerciseCollection.getExercises().get(3).getName(), "Squats");
            
            plan = jsonReaderPlan.readPlan();
            assertEquals(plan.getExercises().size(), 3);
            assertEquals(plan.getExercises().get(0).getName(), "Push-Ups");
            assertEquals(plan.getExercises().get(1).getName(), "Push-Ups");
            assertEquals(plan.getExercises().get(2).getName(), "Sit-Ups");
        } catch (IOException e) {
            fail("Exception should not be thrown");
        }
    }




}
