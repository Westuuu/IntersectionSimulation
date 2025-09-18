package com.intersectionsimulation.model.command;

import com.intersectionsimulation.model.Lane;
import com.intersectionsimulation.service.LaneMapperService;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AddVehicleCommand extends Command {
    private final String vehicleId;
    private final Lane startLane;
    private final Lane endLane;

    public AddVehicleCommand(String vehicleId, String startLane, String endLane, LaneMapperService laneMapperService) {
        this.vehicleId = vehicleId;
        this.startLane = laneMapperService.resolveLane(startLane);
        this.endLane = laneMapperService.resolveLane(endLane);
    }
}
