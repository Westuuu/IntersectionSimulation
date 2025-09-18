package com.intersectionsimulation.model;

import com.intersectionsimulation.model.enums.TrafficLightState;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class TrafficLight {
    private TrafficLightState currentState;
    private final Lane lane;
    private SignalGroup signalGroup;

    public void nextTrafficLightState() {
        switch (currentState) {
            case GREEN ->  {
                currentState = TrafficLightState.YELLOW;
            }
            case YELLOW ->  {
                currentState = TrafficLightState.RED;
            }
            case RED ->  {
                currentState = TrafficLightState.YELLOW_RED;
            }
            case YELLOW_RED ->  {
                currentState = TrafficLightState.GREEN;
            }

        }
    }
}
