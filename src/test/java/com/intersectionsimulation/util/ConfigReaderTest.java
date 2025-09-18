package com.intersectionsimulation.util;

import com.intersectionsimulation.model.command.AddVehicleCommand;
import com.intersectionsimulation.model.command.Command;
import com.intersectionsimulation.model.command.StepCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ConfigReaderTest {
    @Autowired
    private ConfigReader configReader;

    List<Command> commands;

    @BeforeEach
    public void setup() {
        List<Command> commands = configReader.readCommands();
    }

    @Test
    public void readCommandsSuccessfully() {
        Assertions.assertEquals(8, commands.size());
        Assertions.assertInstanceOf(AddVehicleCommand.class, commands.get(0));
        Assertions.assertInstanceOf(AddVehicleCommand.class, commands.get(1));
        Assertions.assertInstanceOf(StepCommand.class, commands.get(2));
        Assertions.assertInstanceOf(StepCommand.class, commands.get(3));
        Assertions.assertInstanceOf(AddVehicleCommand.class, commands.get(4));
        Assertions.assertInstanceOf(AddVehicleCommand.class, commands.get(5));
        Assertions.assertInstanceOf(StepCommand.class, commands.get(6));
        Assertions.assertInstanceOf(StepCommand.class, commands.get(7));
    }

    @Test
    public void readAddVehicleCommandPropertiesSuccesfully() {
        Assertions.assertInstanceOf(AddVehicleCommand.class, commands.getFirst());
        AddVehicleCommand addVehicleCommand = (AddVehicleCommand) commands.getFirst();

        Assertions.assertEquals("vehicle1", addVehicleCommand.getVehicleId());
        Assertions.assertEquals("south", addVehicleCommand.getStartLane().getDirection().toString());
        Assertions.assertEquals("north", addVehicleCommand.getEndLane().getDirection().toString());
    }

    @Test
    public void readStepCommandPropertiesSuccesfully() {
        Assertions.assertInstanceOf(StepCommand.class, commands.get(2));
    }
}
