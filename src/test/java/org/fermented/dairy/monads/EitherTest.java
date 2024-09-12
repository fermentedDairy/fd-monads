package org.fermented.dairy.monads;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class EitherTest {

    @Test
    @DisplayName("""
            When constructing an either with a left:
             1. the left should be present and available
             2. the right should not be present and should throw an exception if fetched""")
    void eitherWithALeft(){
        final Either<Integer, Long> left = Either.ofLeft(123456);
        assertAll(
                () -> assertTrue(left.hasLeft()),
                () -> assertFalse(left.hasRight()),
                () -> assertEquals(123456, left.left()),
                () -> {
                    final NoSuchElementException ex = assertThrows(NoSuchElementException.class, left::right);
                    assertEquals("Right value not present",ex.getMessage());
                },
                () -> assertEquals(Math.pow(left.left(), 2), left.mapLeft((i -> Math.pow(i, 2))).left()),
                () -> assertTrue(left.mapLeft((i -> Math.pow(i, 2))).hasLeft()),
                () -> assertFalse(left.mapRight((i -> Math.pow(i, 2))).hasRight())
        );
    }

    @Test
    @DisplayName("""
            When constructing an either with a right:
             1. the right should be present and available
             2. the left should not be present and should throw an exception if fetched""")
    void eitherWithARight(){
        final Either<Integer, Long> right = Either.ofRight(123456L);
        assertAll(
                () -> assertTrue(right.hasRight()),
                () -> assertFalse(right.hasLeft()),
                () -> assertEquals(123456L, right.right()),
                () -> {
                    final NoSuchElementException ex = assertThrows(NoSuchElementException.class, right::left);
                    assertEquals("Left value not present",ex.getMessage());
                },
                () -> assertEquals(Math.pow(right.right(), 2), right.mapRight((i -> Math.pow(i, 2))).right()),
                () -> assertTrue(right.mapRight((i -> Math.pow(i, 2))).hasRight()),
                () -> assertFalse(right.mapRight((i -> Math.pow(i, 2))).hasLeft())
        );
    }
}