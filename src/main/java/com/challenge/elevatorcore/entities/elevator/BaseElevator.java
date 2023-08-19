package com.challenge.elevatorcore.entities.elevator;

import com.challenge.elevatorcore.dtos.ElevatorEvent;
import com.challenge.elevatorcore.dtos.ElevatorStatus;
import com.challenge.elevatorcore.entities.ElevatorPathCalculator;
import com.challenge.elevatorcore.entities.validation.WeightLimitChecker;
import com.challenge.elevatorcore.gateways.ElevatorEventSource;
import lombok.Getter;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Getter
public abstract class BaseElevator {

    private final ElevatorEventSource elevatorEventSource;
    private int currentPosition = 0;
    private BigDecimal currentWeight = new BigDecimal(0);
    private ElevatorLock lock = new ElevatorLock(false, "");
    private List<Integer> currentPath = Collections.emptyList();
    private final String type;

    public BaseElevator(ElevatorEventSource elevatorEventSource, String type) {
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
        Thread.sleep(1000);
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
        currentPath = ElevatorPathCalculator.calculateOptimalPath(elevatorEventSource.fetchAllEvents(), elevatorStatus());
    }

    private String timestamp() {
        LocalDateTime currentTimestamp = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return currentTimestamp.format(formatter);
    }

    protected abstract WeightLimitChecker getWeightLimitChecker();

    @Scheduled(fixedRate = 10000)
    void scheduledMove() {
        lock = (getWeightLimitChecker().overweightLock(currentWeight));
        move();
    }

    public void updateWeight(BigDecimal weight) {
        this.currentWeight = weight;
    }

}
