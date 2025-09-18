package com.intersectionsimulation.service.strategies;

import com.intersectionsimulation.model.SignalGroup;

import java.util.List;

public interface SignalControlStrategy {
    void step();
    void onActivate();

    int getGreenDuration(SignalGroup signalGroup);
    int getRedDuration(SignalGroup signalGroup);

}
