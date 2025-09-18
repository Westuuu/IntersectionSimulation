package com.intersectionsimulation.model;

import com.intersectionsimulation.model.enums.TrafficLightState;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SignalGroup {
    private final List<TrafficLight> trafficLights;
    private int stepsRemainingInCurrentPhase = 0;
    private int greenDuration;

    public SignalGroup(List<TrafficLight> trafficLights) {
        this.trafficLights = trafficLights;
        this.trafficLights.forEach(trafficLight -> {
            trafficLight.setSignalGroup(this);
        });
    }

    public void updateTrafficLightStates() {
        this.trafficLights.forEach(TrafficLight::nextTrafficLightState);
    }

    public void setTrafficLightsStates(TrafficLightState trafficLightState) {
        this.trafficLights.forEach(trafficLight -> {trafficLight.setCurrentState(trafficLightState);});
    }
}
