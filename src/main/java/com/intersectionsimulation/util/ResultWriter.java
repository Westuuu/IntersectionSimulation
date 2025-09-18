package com.intersectionsimulation.util;

import com.intersectionsimulation.model.SimulationTickResult;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class ResultWriter {
    public void start(Path outputFile) {
    }

    public void writeTick(SimulationTickResult simulationTickResult) {
    }

    public void end() {
    }
}
