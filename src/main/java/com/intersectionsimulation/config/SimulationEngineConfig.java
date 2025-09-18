package com.intersectionsimulation.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intersectionsimulation.simulation.SimulationEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.nio.file.Path;
import java.util.List;

@Configuration
public class SimulationEngineConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ApplicationRunner applicationRunner(SimulationEngine simulationEngine,
                                               ResourceLoader resourceLoader,
                                               @Value("${output-file:results.json}") String outputPath,
                                               @Value("${input-file:commands.json}") String inputPath) {
        return args -> {
            try {
                List<String> nonOptions = args.getNonOptionArgs();
                if (nonOptions.size() == 2) {
                    String inputArg = nonOptions.get(0);
                    String outputArg = nonOptions.get(1);
                    String inputLocation = (inputArg.startsWith("classpath:") || inputArg.contains(":")) ? inputArg : "file:" + inputArg;
                    simulationEngine.run(Path.of(outputArg), resourceLoader.getResource(inputLocation));
                } else {
                    String inputLocation = (inputPath.startsWith("classpath:") || inputPath.contains(":")) ? inputPath : "file:" + inputPath;
                    simulationEngine.run(Path.of(outputPath), resourceLoader.getResource(inputLocation));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
