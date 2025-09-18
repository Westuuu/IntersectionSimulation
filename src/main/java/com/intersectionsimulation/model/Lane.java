package com.intersectionsimulation.model;

import com.intersectionsimulation.model.enums.Direction;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.Queue;

@Getter
@Setter
@RequiredArgsConstructor
public class Lane {
    private LinkedList<Vehicle> vehiclesInQueue = new LinkedList<>();
    private TrafficLight trafficLight;
    private Road parentRoad;
    private final Direction direction;

    public void addVehicleToQueue(Vehicle vehicle) {
        vehiclesInQueue.addLast(vehicle);
    }

    public void removeFirstVehicleFromQueue() {
        vehiclesInQueue.pollFirst();
    }

    public Vehicle peekFirstVehicleInQueue() {
        return vehiclesInQueue.peekFirst();
    }

    public int getQueueLength() {
        return vehiclesInQueue.size();
    }
}
