package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestExerciseChoice {
    private ExerciseCollection exerciseChoice;

    @BeforeEach
    void runBefore() {
        exerciseChoice = new ExerciseCollection();
    }

    @Test
    void testConstructor() {
        assertEquals(exerciseChoice.getExercises().size(), 0);
    }

    @Test
    void testAddDefaultExercise() {
        exerciseChoice.addDefaultExercise();
        assertEquals(exerciseChoice.getExercises().get(0).getName(), "Push-Ups");
        assertEquals(exerciseChoice.getExercises().get(1).getName(), "Sit-Ups");
        assertEquals(exerciseChoice.getExercises().get(2).getName(), "Squats");
    }
}
