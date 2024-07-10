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
        assertEquals(exerciseChoice.getExercises().size(), 3);
    }
}
