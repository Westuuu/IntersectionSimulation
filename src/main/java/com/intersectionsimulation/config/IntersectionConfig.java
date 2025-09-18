package com.intersectionsimulation.config;

import com.intersectionsimulation.model.*;
import com.intersectionsimulation.model.enums.Direction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class IntersectionConfig {

    /**
     * Simulation is running on an assumption that there is at most one Intersection instance,
     * Intersection is configured via this bean at the moment, though there should be ideally a config file*/
    @Bean
    public Intersection intersection() {

        Lane northLane = new Lane(Direction.NORTH);
        Lane southLane = new Lane(Direction.SOUTH);
        Lane eastLane = new Lane(Direction.EAST);
        Lane westLane = new Lane(Direction.WEST);

        TrafficLight northLight = new TrafficLight(northLane);
        TrafficLight southLight = new TrafficLight(southLane);
        TrafficLight eastLight = new TrafficLight(eastLane);
        TrafficLight westLight = new TrafficLight(westLane);

        SignalGroup nsSignalGroup = new SignalGroup(List.of(northLight, southLight));
        SignalGroup ewSignalGroup = new SignalGroup(List.of(westLight, eastLight));

        Road northRoad = new Road(Direction.NORTH);
        northRoad.setLanes(List.of(northLane));
        northLane.setParentRoad(northRoad);

        Road southRoad = new Road(Direction.SOUTH);
        southRoad.setLanes(List.of(southLane));
        southLane.setParentRoad(southRoad);

        Road eastRoad = new Road(Direction.EAST);
        eastRoad.setLanes(List.of(eastLane));
        eastLane.setParentRoad(eastRoad);

        Road westRoad = new Road(Direction.WEST);
        westRoad.setLanes(List.of(westLane));
        westLane.setParentRoad(westRoad);

        return new Intersection(List.of(northRoad, southRoad, eastRoad, westRoad), List.of(nsSignalGroup, ewSignalGroup));
    }
}
