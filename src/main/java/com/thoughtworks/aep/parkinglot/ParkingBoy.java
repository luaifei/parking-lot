package com.thoughtworks.aep.parkinglot;

import lombok.Setter;

import java.util.List;

public class ParkingBoy {

    @Setter
    private List<ParkingLot> managedParkingLots;

    public Ticket park(Car car) {
        return managedParkingLots.get(0).park(car);
    }
}
