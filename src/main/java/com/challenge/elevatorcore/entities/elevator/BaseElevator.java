package com.challenge.elevatorcore.entities.elevator;

import com.challenge.elevatorcore.dtos.ElevatorEvent;
import com.challenge.elevatorcore.dtos.ElevatorEventType;
import com.challenge.elevatorcore.dtos.ElevatorLock;
import com.challenge.elevatorcore.dtos.ElevatorStatus;
import com.challenge.elevatorcore.entities.validation.WeightLimitChecker;
import com.challenge.elevatorcore.gateways.events.ElevatorEventSourceGateway;
import lombok.Getter;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Getter
public abstract class BaseElevator {

    private final ElevatorEventSourceGateway elevatorEventSource;
    private int currentPosition = 0;
    private BigDecimal currentWeight = new BigDecimal(0);
    private ElevatorLock lock = new ElevatorLock(false, "");
    private List<Integer> currentPath = Collections.emptyList();
    private final String type;

    public BaseElevator(ElevatorEventSourceGateway elevatorEventSource, String type) {
        this.elevatorEventSource = elevatorEventSource;
        this.type = type;
    }

    protected void move() {
        try {
            updateCurrentPath();
            if (lock.active) {
                System.out.println(timestamp() + " " + type + " - Elevator is locked because of: " + lock.reason);
                return;
            }
            if (currentPath.isEmpty()) {
                System.out.println(timestamp() + " " + type + " - Elevator is idle on floor " + currentPosition);
                return;
            }
            Integer targetFloor = currentPath.stream().findFirst().get();
            System.out.println(timestamp() + " " + type + " - Elevator moving to floor " + targetFloor);
            work();
            System.out.println(timestamp() + " " + type + " - Elevator arrived and is waiting on floor " + targetFloor);
            currentPosition = targetFloor;
            currentPath = currentPath.stream().skip(1).toList();
            work();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void work() throws InterruptedException {
        Thread.sleep(500);
    }

    public void processEvents(List<ElevatorEvent> events) {
        elevatorEventSource.pushEvents(events);
    }

    private ElevatorStatus elevatorStatus() {
        return ElevatorStatus.builder()
                .currentPath(currentPath)
                .currentPosition(currentPosition)
                .build();
    }

    private void updateCurrentPath() {
        List<Integer> newFloors = getNewFloors(elevatorEventSource.fetchAllEvents());
        currentPath = ElevatorPathCalculator.calculateOptimalPath(newFloors, elevatorStatus());
    }

    private static List<Integer> getNewFloors(List<ElevatorEvent> events) {
        List<ElevatorEvent> callEvents = events.stream()
                .filter(it -> ElevatorEventType.CALL_ELEVATOR.equals(it.getEventType()))
                .toList();

        Optional<ElevatorEvent> toFloorEvent = events.stream()
                .filter(it -> ElevatorEventType.SELECT_FLOORS.equals(it.getEventType()))
                .findFirst();

        List<Integer> newFloors = new ArrayList<>(callEvents.stream().map(ElevatorEvent::getFromFloor).toList());

        toFloorEvent.ifPresent(elevatorEvent -> newFloors.addAll(elevatorEvent.getToFloors()));
        return newFloors;
    }

    private String timestamp() {
        LocalDateTime currentTimestamp = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return currentTimestamp.format(formatter);
    }

    protected abstract WeightLimitChecker getWeightLimitChecker();

    @Scheduled(fixedRate = 3000)
    void scheduledMove() {
        lock = (getWeightLimitChecker().overweightLock(currentWeight));
        move();
    }

    public void updateWeight(BigDecimal weight) {
        this.currentWeight = weight;
    }

}
