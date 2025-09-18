package com.intersectionsimulation.service;

import com.intersectionsimulation.model.Intersection;
import com.intersectionsimulation.model.SignalGroup;
import com.intersectionsimulation.model.enums.TrafficLightState;
import com.intersectionsimulation.service.strategies.FixedTimeStrategy;
import com.intersectionsimulation.service.strategies.SignalControlStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SignalGroupService {
    private final List<SignalGroup> signalGroups;
    private final SignalControlStrategy fixedTimeStrategy;
    private final int strategyThreshold;
    private final int vehicleStrategyThreshold;

    private final int strategyChangeCooldown;
    private int cooldown;

    private SignalControlStrategy currentSignalControlStrategy;


    public SignalGroupService(@Qualifier("fixedTimeStrategy") SignalControlStrategy fixedTimeStrategy,
                              Intersection intersection,
                              @Value("${strategy.threshold.pressure:20}") int strategyThreshold,
                              @Value("${strategy.change.cooldown:3}") int strategyChangeCooldown,
                              @Value("${strategy.threshold.vehicle:10}") int vehicleStrategyThreshold) {
        this.fixedTimeStrategy = fixedTimeStrategy;
        this.signalGroups = intersection.getSignalGroups();
        this.strategyThreshold = strategyThreshold;
        this.strategyChangeCooldown = strategyChangeCooldown;
        this.cooldown = strategyChangeCooldown;
        this.currentSignalControlStrategy = fixedTimeStrategy;
        this.vehicleStrategyThreshold = vehicleStrategyThreshold;
        initializeSignalGroupStates();
        this.currentSignalControlStrategy.onActivate();
    }

    private void initializeSignalGroupStates() {
//        More readable than other ways of doing it, also only one signalGroup per intersection can be at Green state during any moment
        this.signalGroups.forEach(signalGroup -> {
            signalGroup.setTrafficLightsStates(TrafficLightState.RED);
        });
        this.signalGroups.getFirst().setTrafficLightsStates(TrafficLightState.GREEN);
    }

    public void handleStep() {
        if (cooldown == 0) {
            SignalControlStrategy previousStrategy = this.currentSignalControlStrategy;
            pickStrategy();
            if (this.currentSignalControlStrategy != previousStrategy) {
                this.currentSignalControlStrategy.onActivate();
                this.cooldown = this.strategyChangeCooldown;
            }
        } else {
            cooldown--;
        }
        this.currentSignalControlStrategy.step();
    }

    private void pickStrategy() {
        Map<SignalGroup, Double> pressureMap = calculatePressure();

        int numberOfSignalGroups = signalGroups.size();
        double averagePressurePerGroup = 1.0 / numberOfSignalGroups;
        double thresholdPressure = averagePressurePerGroup * this.strategyThreshold;

        boolean thresholdExceeded = pressureMap.values().stream()
                .anyMatch(pressure -> pressure > thresholdPressure);

        Map<SignalGroup, Integer> vehiclesPerSignalGroup = calculateVehiclesPerSignalGroup();

        int allVehicles = vehiclesPerSignalGroup.values().stream().mapToInt(Integer::intValue).sum();

//        This could be extended given there is more strategies and more sophisticated heuristic to pick the best strategy
//        or to minimize certain attributes like waiting time or general flow
        if ((!thresholdExceeded) || allVehicles < this.vehicleStrategyThreshold) {
            this.currentSignalControlStrategy = fixedTimeStrategy;
        } else {
//            TODO: Second strategy based on pressure
//            this.currentSignalControlStrategy = adaptiveTimeStrategy;
        }

    }

    private Map<SignalGroup, Integer> calculateVehiclesPerSignalGroup() {
        Map<SignalGroup, Integer> vehiclesPerSignalGroup = new HashMap<>();

        signalGroups.forEach(signalGroup -> {
            Integer signalGroupVehicleCount = signalGroup.getTrafficLights().stream()
                    .mapToInt(trafficLight -> trafficLight.getLane().getVehiclesInQueue().size())
                    .sum();

            vehiclesPerSignalGroup.put(signalGroup, signalGroupVehicleCount);
        });

        return vehiclesPerSignalGroup;
    }

    private Map<SignalGroup, Double> calculatePressure() {
        Map<SignalGroup, Integer> vehiclesPerSignalGroup = calculateVehiclesPerSignalGroup();
        Map<SignalGroup, Double> pressurePerSignalGroup = new HashMap<>();

        int allVehicles = vehiclesPerSignalGroup.values().stream().mapToInt(Integer::intValue).sum();

        vehiclesPerSignalGroup.forEach((signalGroup, count) -> {
            double pressure = Math.min((double) count / allVehicles, 1.0);
            pressurePerSignalGroup.put(signalGroup, pressure);
        });

        return pressurePerSignalGroup;
    }
}
