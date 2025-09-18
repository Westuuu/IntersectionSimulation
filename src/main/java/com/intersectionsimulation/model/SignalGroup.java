package com.intersectionsimulation.model;

import lombok.Data;

import java.util.List;

@Data
public class SignalGroup {
    private final List<TrafficLight> trafficLights;

    public SignalGroup(List<TrafficLight> trafficLights) {
        this.trafficLights = trafficLights;
        this.trafficLights.forEach(trafficLight -> {
            trafficLight.setSignalGroup(this);
        });
    }
}
