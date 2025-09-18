package com.intersectionsimulation.service;

import com.intersectionsimulation.model.*;
import com.intersectionsimulation.model.enums.TrafficLightState;
import com.intersectionsimulation.model.enums.VehiclePosition;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleMovementService {
    private final Intersection intersection;

    public VehicleMovementService(Intersection intersection) {
        this.intersection = intersection;
    }

    public SimulationTickResult handleStep() {
        List<Vehicle> exitedVehicles = new ArrayList<>();
        for (Road road : intersection.getRoads()) {
            for (Lane lane : road.getLanes()) {
                moveFrontVehicleIfPossible(lane, exitedVehicles);
            }
        }
        return new SimulationTickResult(exitedVehicles);
    }

    /**
     * This is very naive implementation which does not take into account collisions at intersection
     */
//    TODO: Handle collisions when vehicle is turning
    private void moveFrontVehicleIfPossible(Lane lane, List<Vehicle> exitedVehicles) {
        Vehicle vehicle = lane.peekFirstVehicleInQueue();
        if (vehicle == null) {
            return;
        }

        VehiclePosition currentPosition = vehicle.getCurrentPosition();
        if (currentPosition == null) {
            currentPosition = VehiclePosition.WAITING;
        }

//        Those which already entered intersection are clear to proceed regardless of traffic light state
        if (currentPosition == VehiclePosition.AT_INTERSECTION) {
            lane.removeFirstVehicleFromQueue();
            vehicle.setCurrentPosition(VehiclePosition.nextVehiclePosition(currentPosition));
            vehicle.setLane(null);
            exitedVehicles.add(vehicle);


            Vehicle nextVehicle = lane.peekFirstVehicleInQueue();
            if (nextVehicle != null) {
                VehiclePosition nextPos = nextVehicle.getCurrentPosition();
                if (nextPos == null) {
                    nextPos = VehiclePosition.WAITING;
                }
                TrafficLightState state = lane.getTrafficLight().getCurrentState();
                if (state == TrafficLightState.GREEN && nextPos == VehiclePosition.WAITING) {
                    nextVehicle.setCurrentPosition(VehiclePosition.AT_INTERSECTION);
                }
            }
            return;
        }

        TrafficLightState state = lane.getTrafficLight().getCurrentState();
        if (state == TrafficLightState.GREEN) {
            vehicle.setCurrentPosition(VehiclePosition.nextVehiclePosition(currentPosition));
        }
    }
}
