package com.intersectionsimulation.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intersectionsimulation.simulation.SimulationEngine;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimulationEngineConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ApplicationRunner applicationRunner(SimulationEngine simulationEngine) {
        return _ -> simulationEngine.runFromConfig();
    }
}
