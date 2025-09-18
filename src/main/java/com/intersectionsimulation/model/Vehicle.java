package com.intersectionsimulation.model;

import com.intersectionsimulation.model.enums.Direction;
import com.intersectionsimulation.model.enums.VehiclePosition;
import lombok.Data;

@Data
public class Vehicle {
    private final String vehicleId;
    private final Road startRoad;
    private final Road endRoad;
    private Lane lane;
    private VehiclePosition currentPosition;

    public Vehicle(String vehicleId, Road startRoad, Road endRoad) {
        this.vehicleId = vehicleId;
        this.startRoad = startRoad;
        this.endRoad = endRoad;

    }
}
