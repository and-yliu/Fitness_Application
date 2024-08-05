package model;

import org.junit.jupiter.api.Test;

import exception.InvalidInputException;

public class TestExecption {
    @Test
    void testException() {
        try {
            throw new InvalidInputException();
        } catch (InvalidInputException e) {
            // pass
        }
    }
}
