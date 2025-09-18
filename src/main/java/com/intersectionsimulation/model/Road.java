package com.intersectionsimulation.model;

import com.intersectionsimulation.model.enums.Direction;
import lombok.*;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class Road {
    /**
     * For first iteration I'm assuming one lane per road but this approach gives possibility to extend the simulation for
     * supporting multiple lanes per road
     */
    private List<Lane> lanes;
    private final Direction direction;
}
