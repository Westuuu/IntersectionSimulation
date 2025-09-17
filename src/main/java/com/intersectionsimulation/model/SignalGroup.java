package com.intersectionsimulation.model;

import lombok.Data;

import java.util.List;

@Data
public class SignalGroup {
    List<TrafficLight> trafficLights;
}
