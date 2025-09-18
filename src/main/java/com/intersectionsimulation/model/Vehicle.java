package com.intersectionsimulation.model;

import com.intersectionsimulation.model.enums.Direction;
import com.intersectionsimulation.model.enums.VehiclePosition;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
        assignLane();
    }

    /**
     * This one should dynamically assign correct lane depending on the direction that this vehicle is going
     * but for simplicity I assume one lane per road for now
     * */
//    TODO:  Create dynamic lane assigment
    private void assignLane() {
        this.lane = this.startRoad.getLanes().stream().findFirst().orElse(null);
    }
}
