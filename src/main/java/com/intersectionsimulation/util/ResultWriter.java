package com.intersectionsimulation.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intersectionsimulation.model.SimulationTickResult;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Component
public class ResultWriter {
    private final ObjectMapper objectMapper;
    private JsonGenerator generator;
    private OutputStream outputStream;

    public ResultWriter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void start(Path outputFile) throws IOException {
        outputStream = Files.newOutputStream(outputFile, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        generator = objectMapper.getFactory().createGenerator(outputStream);
        generator.useDefaultPrettyPrinter();
        generator.writeStartObject();
        generator.writeFieldName("stepStatuses");
        generator.writeStartArray();
    }

    public void writeTick(SimulationTickResult simulationTickResult) throws IOException {
        generator.writeStartObject();
        generator.writeArrayFieldStart("leftVehicles");
        simulationTickResult.vehicles().forEach(vehicle -> {
            try {
                generator.writeString(vehicle.getVehicleId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        generator.writeEndArray();
        generator.writeEndObject();
        generator.flush();
    }

    public void end() throws IOException {
        if (generator != null) {
            generator.writeEndArray();
            generator.writeEndObject();
            generator.flush();
            generator.close();
            generator = null;
        }
        if (outputStream != null) {
            outputStream.close();
            outputStream = null;
        }
    }
}
