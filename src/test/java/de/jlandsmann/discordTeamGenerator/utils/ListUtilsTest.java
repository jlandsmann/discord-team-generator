package de.jlandsmann.discordTeamGenerator.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ListUtilsTest {

    private static final int DEFAULT_LIST_LENGTH = 10;
    private static final int DEFAULT_UPPER_BOUND = 100;
    private static final int DEFAULT_LOWER_BOUND = 1;

    private List<Integer> testList;


    @BeforeEach
    void setUp() {
        this.testList = getRandomIntegers(DEFAULT_LIST_LENGTH);
    }

    private List<Integer> getRandomIntegers(int n) {

        Random random = new Random();
        return random
                .ints(n, DEFAULT_LOWER_BOUND, DEFAULT_UPPER_BOUND)
                .boxed()
                .collect(Collectors.toList())
        ;
    }

    @Test
    void getNext() {
        final var originalLength = testList.size();
        final var originalList = new ArrayList<>(testList);
        final var nextItem = ListUtils.getNext(testList);
        assertAll("should remove random Element from List and return",
                () -> assertEquals(this.testList.size() + 1, originalLength),
                () -> assertTrue(originalList.contains(nextItem)),
                () -> assertFalse(testList.contains(nextItem))
        );
    }

    @Test
    void split() {
        final int[] listLengths = new int[]{0, 1, 2, 3, 7, 10};
        final int[] chunkCounts = new int[]{0, 1, 2, 3, 4, 100};

        for (int listLength : listLengths) {
            for (int chunkCount : chunkCounts) {
                this.split(listLength, chunkCount);
            }
        }

        assertThrows(IllegalArgumentException.class, () -> this.split(0, -1));
    }

    void split(int listLength, int chunkCount) {
        this.testList = getRandomIntegers(listLength);
        List<List<Integer>> chunks = ListUtils.split(this.testList, chunkCount);

        assertAll("should generate " + chunkCount + " chunks as possible of the same length from " + listLength + " items",
                () -> assertEquals(chunkCount, chunks.size()),
                () -> assertHaveSimilarLength(chunks, 1)
        );

    }

    void assertHaveSimilarLength(List<List<Integer>> chunks, int delta) {
        if (chunks.isEmpty()) {
            return;
        }

        final var chunksSizes = chunks
                .stream()
                .mapToInt(List::size)
                .boxed()
                .collect(Collectors.toList())
        ;
        final var minLength = Collections.min(chunksSizes);
        final var maxLength = Collections.max(chunksSizes);
        assertAll("should have similar length",
                () -> assertTrue(maxLength - minLength <= delta)
        );
    }
}