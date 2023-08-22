package com.challenge.elevatorcore.entities.elevator;

import com.challenge.elevatorcore.dtos.ElevatorLock;
import com.challenge.elevatorcore.dtos.ElevatorStatus;
import com.challenge.elevatorcore.entities.validation.WeightLimitChecker;
import com.challenge.elevatorcore.gateways.events.ElevatorEventSourceGateway;
import lombok.Getter;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
public abstract class BaseElevator {

    private final ElevatorEventSourceGateway elevatorEventSource;
    private int currentPosition = 0;
    private BigDecimal currentWeight = new BigDecimal(0);
    private ElevatorLock lock = new ElevatorLock(false, "");
    private List<Integer> currentPath = Collections.emptyList();
    private final String type;
    private String lastLog = "";

    public BaseElevator(ElevatorEventSourceGateway elevatorEventSource, String type) {
        this.elevatorEventSource = elevatorEventSource;
        this.type = type;
    }

    protected void move() {
        try {
            updateCurrentPath();
            if (lock.active) {
                log(" - Elevator is locked because of " + lock.reason);
                return;
            }
            if (currentPath.isEmpty()) {
                log(" - Elevator is idle on floor " + currentPosition);
                return;
            }
            Integer targetFloor = currentPath.stream().findFirst().get();
            log(" - Elevator moving to floor " + targetFloor);
            work();
            log(" - Elevator arrived and is waiting on floor " + targetFloor);
            currentPosition = targetFloor;
            currentPath = currentPath.stream().skip(1).toList();
            work();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void log(String message) {
        if (!Objects.equals(lastLog, message)) {
            System.out.println(timestamp() + " " + type + message);
            lastLog = message;
        }
    }

    private static void work() throws InterruptedException {
        //This simulates the time that the elevator takes to move
        Thread.sleep(500);
    }

    public void processEvents(List<Integer> events) {
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

    @Scheduled(fixedRate = 3000)
    void scheduledMove() {
        lock = (getWeightLimitChecker().overweightLock(currentWeight));
        move();
    }

    public void updateWeight(BigDecimal weight) {
        this.currentWeight = weight;
    }

}
