package com.intersectionsimulation.config;

import com.intersectionsimulation.model.*;
import com.intersectionsimulation.model.enums.Direction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class IntersectionConfig {
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

        Road southRoad = new Road(Direction.SOUTH);
        southRoad.setLanes(List.of(southLane));

        Road eastRoad = new Road(Direction.EAST);
        eastRoad.setLanes(List.of(eastLane));

        Road westRoad = new Road(Direction.WEST);
        westRoad.setLanes(List.of(westLane));

        return new Intersection(List.of(northRoad, southRoad, eastRoad, westRoad), List.of(nsSignalGroup, ewSignalGroup));
    }
}
