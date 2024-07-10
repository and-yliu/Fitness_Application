package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPlan {
    private Plan plan;
    private Exercise exercise1;
    private Exercise exercise2;

    @BeforeEach
    void runBefore() {
        plan = new Plan();
        exercise1 = new Exercise("Push-Ups", "On all fours and push up", "Arm", 60);
        exercise2 = new Exercise("Sit-Ups", "Lie down, bend knees, and lift torso up", "Abs", 60);
    }

    @Test
    void testConstructor() {
        assertTrue(plan.getExercises().isEmpty());
    }

    @Test
    void testAddExercise() {
        plan.addExercise(0, exercise1);
        assertEquals(plan.getExercises().size(), 1);
    }

    @Test
    void testAddExerciseMultiple() {
        plan.addExercise(0, exercise1);
        plan.addExercise(1, exercise2);
        assertEquals(plan.getExercises().size(), 2);
        assertEquals(plan.getExercises().get(0), exercise1);
        assertEquals(plan.getExercises().get(1), exercise2);

        plan.addExercise(0, exercise2);
        assertEquals(plan.getExercises().get(0), exercise2);

    }

    @Test
    void testRemoveExercise() {
        plan.addExercise(0, exercise1);
        plan.addExercise(1, exercise2);
        assertEquals(plan.getExercises().size(), 2);

        plan.removeExercise(0);
        assertEquals(plan.getExercises().size(), 1);
        assertEquals(plan.getExercises().get(0), exercise2);
    }

    @Test
    void testGetTotalDuration() {
        assertEquals(plan.getTotalDuration(), 0);

        plan.addExercise(0, exercise1);
        plan.addExercise(1, exercise2);
        assertEquals(plan.getTotalDuration(), 120);
    }

    @Test
    void testAddRest() {
        plan.addExercise(0, exercise1);
        plan.addExercise(1, exercise2);
        assertEquals(plan.getExercises().size(), 2);

        plan.addRest(1);
        assertEquals(plan.getExercises().size(), 3);
        assertEquals(plan.getExercises().get(1).getName(), "Rest");
    }

    @Test
    void testCompleteExercise() {
        plan.addExercise(0, exercise1);
        plan.addExercise(1, exercise2);
        assertEquals(plan.getExercises().size(), 2);

        plan.completeExercise(exercise1);
        assertEquals(plan.getExercises().size(), 1);
        assertEquals(plan.getCompletedExercise().size(), 1);
        assertEquals(plan.getCompletedExercise().get(0), exercise1);
    }

    @Test
    void testCompleteExerciseMultiple() {
        plan.addExercise(0, exercise1);
        plan.addExercise(1, exercise1);
        assertEquals(plan.getExercises().size(), 2);

        plan.completeExercise(exercise1);
        assertEquals(plan.getExercises().size(), 1);
        assertEquals(plan.getCompletedExercise().size(), 1);
        assertEquals(plan.getCompletedExercise().get(0), exercise1);
    }

}
