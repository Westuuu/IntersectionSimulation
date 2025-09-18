package com.intersectionsimulation.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intersectionsimulation.model.SimulationTickResult;
import com.intersectionsimulation.model.Vehicle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ResultWriterTest {

    @Autowired
    private ResultWriter resultWriter;

    @Autowired
    private ObjectMapper objectMapper;

    private Path outputFile;

    private Vehicle v1, v2, v3, v4;


    @BeforeEach
    public void setUp(@TempDir Path tempDir) {
        this.outputFile = tempDir.resolve("results.json");

        v1 = mock(Vehicle.class); when(v1.getVehicleId()).thenReturn("vehicle1");
        v2 = mock(Vehicle.class); when(v2.getVehicleId()).thenReturn("vehicle2");
        v3 = mock(Vehicle.class); when(v3.getVehicleId()).thenReturn("vehicle3");
        v4 = mock(Vehicle.class); when(v4.getVehicleId()).thenReturn("vehicle4");
    }

    @Test
    public void writesRequiredJson() throws IOException {
        resultWriter.start(outputFile);
        resultWriter.writeTick(new SimulationTickResult(List.of(v1, v2)));
        resultWriter.writeTick(new SimulationTickResult(List.of()));
        resultWriter.writeTick(new SimulationTickResult(List.of(v3)));
        resultWriter.writeTick(new SimulationTickResult(List.of(v4)));
        resultWriter.end();

        JsonNode rootNode = objectMapper.readTree(outputFile.toFile());
        Assertions.assertTrue(rootNode.has("stepStatuses"));
        JsonNode stepStatuses = rootNode.get("stepStatuses");
        Assertions.assertTrue(stepStatuses.isArray());
        Assertions.assertEquals(4, stepStatuses.size());

        assertVehicleIds(List.of(v1, v2), stepStatuses.get(0));
        assertVehicleIds(List.of(), stepStatuses.get(1));
        assertVehicleIds(List.of(v3), stepStatuses.get(2));
        assertVehicleIds(List.of(v4), stepStatuses.get(3));
    }

    private void assertVehicleIds(List<Vehicle> vehicles, JsonNode stepStatus) {
        List<String> actualVehicleIds = new ArrayList<>();
        vehicles.forEach(vehicle -> actualVehicleIds.add(vehicle.getVehicleId()));

        List<String> vehiclesInJson = new ArrayList<>();
        stepStatus.get("leftVehicles").forEach(vehicle -> vehiclesInJson.add(vehicle.asText()));

//       For the sake of tests it is assumed that I know the order of vehicles added,
//       so I don't have to worry about getting false results
        Assertions.assertEquals(vehiclesInJson, actualVehicleIds);
    }
}
