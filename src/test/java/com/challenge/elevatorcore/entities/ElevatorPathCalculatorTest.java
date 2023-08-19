package com.challenge.elevatorcore.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.challenge.elevatorcore.dtos.ElevatorEvent;
import com.challenge.elevatorcore.dtos.ElevatorEventType;
import com.challenge.elevatorcore.dtos.ElevatorStatus;
import com.challenge.elevatorcore.dtos.ElevatorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ElevatorPathCalculatorTest {

    private ElevatorPathCalculator calculator;
    private final static ElevatorType PUBLIC = ElevatorType.PUBLIC;
    private final static ElevatorEventType CALL_ELEVATOR = ElevatorEventType.CALL_ELEVATOR;
    private final static ElevatorEventType SELECT_FLOORS = ElevatorEventType.SELECT_FLOORS;

    @BeforeEach
    public void setup() {
        calculator = new ElevatorPathCalculator(); //TODO fix this, it is static
    }

    @Test
    public void case_1() {
        List<ElevatorEvent> events = Collections.singletonList(
                ElevatorEvent.builder()
                        .elevatorType(PUBLIC)
                        .eventType(CALL_ELEVATOR)
                        .fromFloor(8)
                        .accessKey(7)
                        .build()
        );

        List<Integer> expectedPath = List.of(4, 5, 6, 8);
        List<Integer> actualPath = calculator.calculateOptimalPath(events, report(0, Arrays.asList(4, 5, 6)));

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
        List<ElevatorEvent> events = Arrays.asList(
                ElevatorEvent.builder()
                        .elevatorType(PUBLIC)
                        .eventType(CALL_ELEVATOR)
                        .fromFloor(8)
                        .accessKey(7)
                        .build(),
                ElevatorEvent.builder()
                        .elevatorType(PUBLIC)
                        .eventType(CALL_ELEVATOR)
                        .fromFloor(4)
                        .accessKey(7)
                        .build(),
                ElevatorEvent.builder()
                        .elevatorType(PUBLIC)
                        .eventType(CALL_ELEVATOR)
                        .fromFloor(7)
                        .accessKey(7)
                        .build()
        );

        List<Integer> expectedPath = Arrays.asList(4, 5, 7, 8, 10, 17);
        List<Integer> actualPath = calculator.calculateOptimalPath(events, report(3, Arrays.asList(5, 10, 17)));

        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void case_3() {
        List<ElevatorEvent> events = Collections.singletonList(
                ElevatorEvent.builder()
                        .elevatorType(PUBLIC)
                        .eventType(SELECT_FLOORS)
                        .fromFloor(3)
                        .toFloors(List.of(10))
                        .accessKey("xyz123")
                        .build()
        );

        List<Integer> expectedPath = List.of(4, 5, 10);
        List<Integer> actualPath = calculator.calculateOptimalPath(events, report(3, List.of(4, 5)));

        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void case_4() {
        List<ElevatorEvent> events = Arrays.asList(
                ElevatorEvent.builder()
                        .elevatorType(PUBLIC)
                        .eventType(CALL_ELEVATOR)
                        .fromFloor(34)
                        .accessKey("aaa111")
                        .build(),
                ElevatorEvent.builder()
                        .elevatorType(PUBLIC)
                        .eventType(SELECT_FLOORS)
                        .fromFloor(5)
                        .toFloors(List.of(9))
                        .accessKey("bbb222")
                        .build()
        );

        List<Integer> expectedPath = List.of(9, 34);
        List<Integer> actualPath = calculator.calculateOptimalPath(events, report(5, List.of()));

        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void case_5() {
        List<ElevatorEvent> events = Arrays.asList(
                ElevatorEvent.builder()
                        .elevatorType(PUBLIC)
                        .eventType(CALL_ELEVATOR)
                        .fromFloor(34)
                        .accessKey("aaa111")
                        .build(),
                ElevatorEvent.builder()
                        .elevatorType(PUBLIC)
                        .eventType(SELECT_FLOORS)
                        .fromFloor(18)
                        .toFloors(List.of(9, 45))
                        .accessKey("bbb222")
                        .build()
        );

        List<Integer> expectedPath = List.of(17, 13, 9, 3, -1, 21, 22, 34, 45);
        List<Integer> actualPath = calculator.calculateOptimalPath(events, report(18, List.of(17, 13, 3, -1, 21, 22)));

        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void case_6() {
        List<ElevatorEvent> events = Arrays.asList(
                ElevatorEvent.builder()
                        .elevatorType(PUBLIC)
                        .eventType(CALL_ELEVATOR)
                        .fromFloor(34)
                        .accessKey("aaa111")
                        .build(),
                ElevatorEvent.builder()
                        .elevatorType(PUBLIC)
                        .eventType(SELECT_FLOORS)
                        .fromFloor(18)
                        .toFloors(List.of(9, 45))
                        .accessKey("bbb222")
                        .build()
        );

        List<Integer> expectedPath = List.of(21, 22, 34, 45, 17, 13, 9, 3, -1);
        List<Integer> actualPath = calculator.calculateOptimalPath(events, report(18, List.of(21, 22, 17, 13, 3, -1)));

        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void case_7() {
        List<ElevatorEvent> events = Arrays.asList(
                ElevatorEvent.builder()
                        .elevatorType(PUBLIC)
                        .eventType(SELECT_FLOORS)
                        .fromFloor(3)
                        .toFloors(List.of(8, 10))
                        .accessKey("xyz123")
                        .build(),
                ElevatorEvent.builder()
                        .elevatorType(PUBLIC)
                        .eventType(CALL_ELEVATOR)
                        .fromFloor(6)
                        .accessKey("abc456")
                        .build()
        );

        List<Integer> expectedPath = List.of(6, 7, 8, 10, 12, 21);
        List<Integer> actualPath = calculator.calculateOptimalPath(events, report(3, List.of(7, 12, 21)));

        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void case_8() {
        List<ElevatorEvent> events = Arrays.asList(
                ElevatorEvent.builder()
                        .elevatorType(PUBLIC)
                        .eventType(CALL_ELEVATOR)
                        .fromFloor(5)
                        .accessKey("aaa111")
                        .build(),
                ElevatorEvent.builder()
                        .elevatorType(PUBLIC)
                        .eventType(SELECT_FLOORS)
                        .fromFloor(4)
                        .toFloors(List.of(6, 9))
                        .accessKey("bbb222")
                        .build()
        );

        List<Integer> expectedPath = List.of(5, 6, 9);
        List<Integer> actualPath = calculator.calculateOptimalPath(events, report(4, List.of()));

        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void case_9() {
        List<ElevatorEvent> events = Arrays.asList(
                ElevatorEvent.builder()
                        .elevatorType(PUBLIC)
                        .eventType(CALL_ELEVATOR)
                        .fromFloor(2)
                        .accessKey("ccc333")
                        .build(),
                ElevatorEvent.builder()
                        .elevatorType(PUBLIC)
                        .eventType(CALL_ELEVATOR)
                        .fromFloor(10)
                        .accessKey("ddd444")
                        .build()
        );

        List<Integer> expectedPath = List.of(6, 7, 8, 10, 2);
        List<Integer> actualPath = calculator.calculateOptimalPath(events, report(5, List.of(6, 7, 8)));

        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void case_10() {
        List<ElevatorEvent> events = Arrays.asList(
                ElevatorEvent.builder()
                        .elevatorType(PUBLIC)
                        .eventType(CALL_ELEVATOR)
                        .fromFloor(50)
                        .accessKey("ccc333")
                        .build(),
                ElevatorEvent.builder()
                        .elevatorType(PUBLIC)
                        .eventType(CALL_ELEVATOR)
                        .fromFloor(17)
                        .accessKey("ddd444")
                        .build()
        );

        List<Integer> expectedPath = List.of(20, 17, 10, 0, 50);
        List<Integer> actualPath = calculator.calculateOptimalPath(events, report(45, List.of(20, 10, 0)));

        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void case_11() {
        List<ElevatorEvent> events = Collections.singletonList(
                ElevatorEvent.builder()
                        .elevatorType(PUBLIC)
                        .eventType(SELECT_FLOORS)
                        .fromFloor(40)
                        .toFloors(List.of(36, 38))
                        .accessKey("bbb222")
                        .build()
        );

        List<Integer> expectedPath = List.of(38, 36);
        List<Integer> actualPath = calculator.calculateOptimalPath(events, report(40, List.of()));

        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void case_12() {
        List<ElevatorEvent> events = Collections.singletonList(
                ElevatorEvent.builder()
                        .elevatorType(PUBLIC)
                        .eventType(SELECT_FLOORS)
                        .fromFloor(40)
                        .toFloors(List.of(0, 32, 31, 45, 46))
                        .accessKey("bbb222")
                        .build()
        );

        List<Integer> expectedPath = List.of(45, 46, 32, 31, 0);
        List<Integer> actualPath = calculator.calculateOptimalPath(events, report(40, List.of()));

        assertEquals(expectedPath, actualPath);
    }

}
