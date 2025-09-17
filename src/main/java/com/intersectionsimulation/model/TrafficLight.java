package com.intersectionsimulation.model;

import com.intersectionsimulation.model.enums.TrafficLightState;
import lombok.Data;

@Data
public class TrafficLight {
    TrafficLightState currentState;
}
