package com.intersectionsimulation.service.strategies;

import com.intersectionsimulation.model.SignalGroup;
import com.intersectionsimulation.model.enums.TrafficLightState;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("fixedTimeStrategy")
public class FixedTimeStrategy implements SignalControlStrategy {
    private final int greenDuration;
    private final int yellowDuration;
    private final int redDuration;
    private final int yellowRedDuration;

    private final List<SignalGroup> signalGroups;

    public FixedTimeStrategy(@Value("${traffic.light.green.duration:10}") int greenDuration,
                             @Value("${traffic.light.yellowStates:1}") int yellowDuration,
                             @Value("${traffic.light.yellowStates:1}") int yellowRedDuration,
                             List<SignalGroup> signalGroups) {
        this.greenDuration = greenDuration;
        this.yellowDuration = yellowDuration;
        this.yellowRedDuration = yellowRedDuration;
        this.signalGroups = signalGroups;
        this.redDuration = calculateRedDuration();
        onActivate();
    }

    private int calculateRedDuration() {
        int groupCount = signalGroups.size();
        int cycleTimePerGroup = greenDuration + yellowDuration + yellowRedDuration;
        return cycleTimePerGroup * groupCount;
    }

    @Override
    public void onActivate() {
        signalGroups.forEach(this::resetDurationForCurrentState);
    }

    @Override
    public void step() {
        signalGroups.forEach(signalGroup -> {
            int remainingSteps = signalGroup.getStepsRemainingInCurrentPhase() - 1;
            signalGroup.setStepsRemainingInCurrentPhase(remainingSteps);

            if (remainingSteps == 0) {
                signalGroup.updateTrafficLightStates();
                resetDurationForCurrentState(signalGroup);
            }
        });
    }

    private void resetDurationForCurrentState(SignalGroup signalGroup) {
        TrafficLightState state = signalGroup.getTrafficLights().getFirst().getCurrentState();
        int duration = switch (state) {
            case GREEN -> signalGroup.getGreenDuration();
            case YELLOW -> yellowDuration;
            case RED -> redDuration;
            case YELLOW_RED -> yellowRedDuration;
        };
        signalGroup.setStepsRemainingInCurrentPhase(duration);
    }

    @Override
    public int getGreenDuration(SignalGroup signalGroup) {
        return this.greenDuration;
    }

    @Override
    public int getRedDuration(SignalGroup signalGroup) {
        return this.redDuration;
    }

}
