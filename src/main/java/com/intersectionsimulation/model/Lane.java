package com.intersectionsimulation.model;

import com.intersectionsimulation.model.enums.Direction;
import lombok.Data;

import java.util.LinkedList;
import java.util.Queue;

@Data
public class Lane {
    private Queue<Vehicle> vehiclesInQueue = new LinkedList<>();
    private TrafficLight trafficLight;
    private Road parentRoad;
    private final Direction direction;
}
