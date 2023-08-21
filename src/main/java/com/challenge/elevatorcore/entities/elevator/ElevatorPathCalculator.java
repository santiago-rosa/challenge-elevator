package com.challenge.elevatorcore.entities.elevator;

import com.challenge.elevatorcore.dtos.ElevatorStatus;

import java.util.*;
import java.util.stream.Collectors;

public class ElevatorPathCalculator {

    //TODO this class could implement an interface, so we could have different strategies

    public static List<Integer> calculateOptimalPath(List<Integer> newFloors, ElevatorStatus report) {

        if (newFloors.isEmpty()) return report.currentPath;
        Integer currentPosition = report.currentPosition;
        List<Integer> currentPath = report.currentPath;

        int elevatorDirection = currentPath.isEmpty() ? 0 : currentPosition.compareTo(currentPath.get(0)); // -1 is up, 0 is idle, 1 is down

        Set<Integer> newPath = new LinkedHashSet<>(currentPath);

        List<Integer> output = new ArrayList<>();
        newPath.addAll(newFloors);
        if (elevatorDirection == 0) { //Idle
            Integer highestFloorInNewFloors = Collections.max(newFloors);
            Integer lowestFloorInNewFloors = Collections.min(newFloors);
            if (currentPosition > highestFloorInNewFloors) {
                output = desc(new ArrayList<>(newPath));
            } else if (currentPosition < lowestFloorInNewFloors) {
                output = asc(new ArrayList<>(newPath));
            } else {
                movingUpStrategy(newPath, currentPosition, output);
            }
        } else if (elevatorDirection == -1) { //moving up
            movingUpStrategy(newPath, currentPosition, output);
        } else { //moving down
            movingDownStrategy(newPath, currentPosition, output);
        }

        return new ArrayList<>(output);
    }


    private static void movingDownStrategy(Set<Integer> newPath, Integer currentPosition, List<Integer> output) {
        List<Integer> ordered = desc(new ArrayList<>(newPath));
        List<Integer> floorsAbove = asc(getFloorsAbove(currentPosition, ordered));
        List<Integer> floorsBelow = getFloorsBelow(currentPosition, ordered);
        addAll(output, floorsBelow, floorsAbove);
    }

    private static void addAll(List<Integer> output, List<Integer> floorsBelow, List<Integer> floorsAbove) {
        output.addAll(floorsBelow);
        output.addAll(floorsAbove);
    }

    private static void movingUpStrategy(Set<Integer> newPath, Integer currentPosition, List<Integer> output) {
        List<Integer> ordered = asc(new ArrayList<>(newPath));
        List<Integer> floorsAbove = getFloorsAbove(currentPosition, ordered);
        List<Integer> floorsBelow = desc(getFloorsBelow(currentPosition, ordered));
        addAll(output, floorsAbove, floorsBelow);
    }

    private static List<Integer> getFloorsBelow(Integer currentPosition, List<Integer> ordered) {
        return ordered.stream().filter(floor -> floor < currentPosition).collect(Collectors.toList());
    }

    private static List<Integer> getFloorsAbove(Integer currentPosition, List<Integer> ordered) {
        return ordered.stream().filter(floor -> floor > currentPosition).collect(Collectors.toList());
    }

    private static List<Integer> asc(List<Integer> input) {
        return input.stream()
                .sorted(Comparator.naturalOrder())
                .toList();
    }

    private static List<Integer> desc(List<Integer> input) {
        return input.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

}
