package com.intersectionsimulation.simulation;

import com.intersectionsimulation.model.Lane;
import com.intersectionsimulation.model.Road;
import com.intersectionsimulation.model.Vehicle;
import com.intersectionsimulation.model.command.AddVehicleCommand;
import com.intersectionsimulation.model.command.Command;
import com.intersectionsimulation.model.command.StepCommand;
import com.intersectionsimulation.service.CollisionService;
import com.intersectionsimulation.service.LaneMapperService;
import com.intersectionsimulation.service.SignalGroupService;
import com.intersectionsimulation.util.ConfigReader;
import com.intersectionsimulation.util.ResultWriter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Component
public class SimulationEngine {
    private final ConfigReader configReader;
    private final ResultWriter resultWriter;
    private final LaneMapperService laneMapperService;
    private final SignalGroupService signalGroupService;
    private final CollisionService collisionService;

    public SimulationEngine(ConfigReader configReader,
                            ResultWriter resultWriter,
                            LaneMapperService laneMapperService,
                            SignalGroupService signalGroupService,
                            CollisionService collisionService) {
        this.configReader = configReader;
        this.resultWriter = resultWriter;
        this.laneMapperService = laneMapperService;
        this.signalGroupService = signalGroupService;
        this.collisionService = collisionService;
    }

    public void runFromConfig(Path outputFile) throws IOException {
        resultWriter.start(outputFile);
        List<Command> commandsList = configReader.readCommands();
        commandsList.forEach(this::process);
        resultWriter.end();
    }

    private void process(Command command) {
        if (command instanceof AddVehicleCommand) {
            handleAddVehicle((AddVehicleCommand) command);
        } else if (command instanceof StepCommand) {
            step();
        }
    }

    private void handleAddVehicle(AddVehicleCommand command) {
        // This might seem overkill for one lane per road
        // but required in my view to leave space for implementing varying lane count
        Lane startLane = laneMapperService.resolveLane(command.getStartRoad());
        Road startRoad = startLane.getParentRoad();

        Lane endLane = laneMapperService.resolveLane(command.getEndRoad());
        Road endRoad = endLane.getParentRoad();

        Vehicle vehicle = new Vehicle(command.getVehicleId(), startRoad, endRoad);

        startLane.addVehicleToQueue(vehicle);
    }

    public void step(){
        signalGroupService.handleStep();
    }
}
