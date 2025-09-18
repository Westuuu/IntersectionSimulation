package com.intersectionsimulation.model.enums;

public enum VehiclePosition {
    WAITING,
    AT_INTERSECTION,
    EXITED;

    public static VehiclePosition nextVehiclePosition(VehiclePosition currentVehiclePosition) {
        switch (currentVehiclePosition) {
            case WAITING -> {
                return AT_INTERSECTION;
            }
            case AT_INTERSECTION -> {
                return EXITED;
            }
            default -> {
                return currentVehiclePosition;
            }
        }
    }


}
