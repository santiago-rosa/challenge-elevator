package com.challenge.elevatorcore.entities.elevator;

import com.challenge.elevatorcore.dtos.ElevatorStatus;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ElevatorPathCalculatorTest {

    @Test
    public void case_1() {
        List<Integer> expectedPath = List.of(4, 5, 6, 8);

        List<Integer> actualPath = ElevatorPathCalculator.calculateOptimalPath(List.of(8), report(0, Arrays.asList(4, 5, 6)));

        assertEquals(expectedPath, actualPath);
    }

    private ElevatorStatus report(int currentPosition, List<Integer> currentPath) {
        return ElevatorStatus.builder()
                .currentPosition(currentPosition)
                .currentPath(currentPath)
                .build();
    }

    @Test
    public void case_2() {
        List<Integer> expectedPath = Arrays.asList(4, 5, 7, 8, 10, 17);

        List<Integer> actualPath = ElevatorPathCalculator.calculateOptimalPath(List.of(8, 4, 7), report(3, Arrays.asList(5, 10, 17)));

        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void case_3() {
        List<Integer> expectedPath = List.of(4, 5, 10);

        List<Integer> actualPath = ElevatorPathCalculator.calculateOptimalPath(List.of(10), report(3, List.of(4, 5)));

        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void case_4() {
        List<Integer> expectedPath = List.of(9, 34);

        List<Integer> actualPath = ElevatorPathCalculator.calculateOptimalPath(List.of(34, 9), report(5, List.of()));

        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void case_5() {
        List<Integer> expectedPath = List.of(17, 13, 9, 3, -1, 21, 22, 34, 45);

        List<Integer> actualPath = ElevatorPathCalculator.calculateOptimalPath(List.of(34, 9, 45), report(18, List.of(17, 13, 3, -1, 21, 22)));

        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void case_6() {
        List<Integer> expectedPath = List.of(21, 22, 34, 45, 17, 13, 9, 3, -1);

        List<Integer> actualPath = ElevatorPathCalculator.calculateOptimalPath(List.of(34, 9, 45), report(18, List.of(21, 22, 17, 13, 3, -1)));

        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void case_7() {
        List<Integer> expectedPath = List.of(6, 7, 8, 10, 12, 21);

        List<Integer> actualPath = ElevatorPathCalculator.calculateOptimalPath(List.of(8, 10, 6), report(3, List.of(7, 12, 21)));

        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void case_8() {
        List<Integer> expectedPath = List.of(5, 6, 9);

        List<Integer> actualPath = ElevatorPathCalculator.calculateOptimalPath(List.of(5, 6, 9), report(4, List.of()));

        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void case_9() {
        List<Integer> expectedPath = List.of(6, 7, 8, 10, 2);

        List<Integer> actualPath = ElevatorPathCalculator.calculateOptimalPath(List.of(2, 10), report(5, List.of(6, 7, 8)));

        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void case_10() {
        List<Integer> expectedPath = List.of(20, 17, 10, 0, 50);

        List<Integer> actualPath = ElevatorPathCalculator.calculateOptimalPath(List.of(50, 17), report(45, List.of(20, 10, 0)));

        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void case_11() {
        List<Integer> expectedPath = List.of(38, 36);

        List<Integer> actualPath = ElevatorPathCalculator.calculateOptimalPath(List.of(36, 38), report(40, List.of()));

        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void case_12() {
        List<Integer> expectedPath = List.of(45, 46, 32, 31, 0);

        List<Integer> actualPath = ElevatorPathCalculator.calculateOptimalPath(List.of(0, 32, 31, 45, 46), report(40, List.of()));

        assertEquals(expectedPath, actualPath);
    }

}
