package com.intersectionsimulation.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intersectionsimulation.simulation.SimulationEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Configuration
public class SimulationEngineConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ApplicationRunner applicationRunner(SimulationEngine simulationEngine,
                                               @Value("${output-file:results.json}") String outputPath,
                                               @Value("${input-file:commands.json}") String inputFile) {
        return args -> {
            try {
                simulationEngine.runFromConfig(Path.of(outputPath));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
