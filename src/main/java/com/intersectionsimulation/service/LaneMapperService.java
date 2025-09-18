package com.intersectionsimulation.service;

import com.intersectionsimulation.model.Intersection;
import com.intersectionsimulation.model.Lane;
import com.intersectionsimulation.model.Road;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LaneMapperService {
    private final Intersection intersection;
    @Getter
    private final Map<String, Lane> lanesMapping = new HashMap<>();

    public LaneMapperService(Intersection intersection) {
        this.intersection = intersection;
        initializeLanes();
    }

    private void initializeLanes() {
        for (Road road : intersection.getRoads()) {
            for (Lane lane : road.getLanes()) {
                lanesMapping.put(lane.getDirection().toString().toLowerCase(), lane);
            }
        }
    }

    public Lane resolveLane(String laneDirection) {
        Lane lane = lanesMapping.get(laneDirection.toLowerCase().trim());
        if (lane == null) {
            throw new IllegalArgumentException("Unknown lane direction: " + laneDirection + ", available lanes: " + lanesMapping.keySet());
        }
        return lane;
    }
}
