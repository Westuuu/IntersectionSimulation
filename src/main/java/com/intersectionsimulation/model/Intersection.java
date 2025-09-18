package com.intersectionsimulation.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Intersection {
    private List<Road> roads;
    private List<SignalGroup> signalGroups;
}
