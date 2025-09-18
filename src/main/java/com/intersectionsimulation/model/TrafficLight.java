package com.intersectionsimulation.model;

import com.intersectionsimulation.model.enums.TrafficLightState;
import lombok.Data;

@Data
public class TrafficLight {
    private TrafficLightState currentState;
    private final Lane lane;
    private SignalGroup signalGroup;
}
