package model;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestExercise {
    private Exercise exercise;
    private String name;
    private String description;
    private String bodyPart;
    private int duration;

    @BeforeEach
    void runBefore() {
        name = "Test";
        description = "Testing exercise";
        bodyPart = "Abs";
        duration = 30;
        exercise = new Exercise(name, description, bodyPart, duration);
    }

    @Test
    void testConstructor() {
        assertEquals(exercise.getName(), name);
        assertEquals(exercise.getDescription(), description);
        assertEquals(exercise.getBodyPart(), bodyPart);
        assertEquals(exercise.getDuration(), duration);
    }

    @Test
    void testSetDuration() {
        exercise.setDuration(10);
        assertEquals(exercise.getDuration(), 10);
    }

    @Test
    void testToString() {
        assertEquals(exercise.toString(), "Test: 30 sec");
    }
}
