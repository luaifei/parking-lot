package com.thoughtworks.aep.parkinglot;

import lombok.NonNull;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class SmartParkingBoy extends ParkingBoyTemplate {
    public SmartParkingBoy(@NonNull List<ParkingLot> parkingLots) {
        super(parkingLots);
    }

    @Override
    public Optional<ParkingLot> selectParkingLot() {
        return managedParkingLots.stream().max(Comparator.comparing(ParkingLot::getCapacity));
    }
}
