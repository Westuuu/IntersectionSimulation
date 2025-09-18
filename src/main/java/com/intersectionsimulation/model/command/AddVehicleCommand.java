package com.intersectionsimulation.model.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Data
@Getter
public class AddVehicleCommand extends Command {
    private final String vehicleId;
    private final String startRoad;
    private final String endRoad;

    @JsonCreator
    public AddVehicleCommand(
            @JsonProperty("vehicleId") String vehicleId,
            @JsonProperty("startRoad") String startRoad,
            @JsonProperty("endRoad") String endRoad) {
        this.vehicleId = vehicleId;
        this.startRoad = startRoad;
        this.endRoad = endRoad;
    }
}
