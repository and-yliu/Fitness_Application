package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestExerciseChoice {
    private ExerciseDatabase exerciseChoice;

    @BeforeEach
    void runBefore() {
        exerciseChoice = new ExerciseDatabase();
    }

    @Test
    void testConstructor(){
        assertEquals(exerciseChoice.getExercises().size(), 3);
    }
}
