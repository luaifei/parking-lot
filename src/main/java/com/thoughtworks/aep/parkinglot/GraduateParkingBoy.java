package com.thoughtworks.aep.parkinglot;

import com.thoughtworks.aep.parkinglot.exception.NoEnoughParkingLotException;
import com.thoughtworks.aep.parkinglot.exception.PickCarFailureException;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public class GraduateParkingBoy extends ParkingBoyTemplate {

    public GraduateParkingBoy(@NonNull List<ParkingLot> managedParkingLots) {
        super(managedParkingLots);
    }

    @Override
    public Optional<ParkingLot> selectParkingLot() {
        return managedParkingLots.stream()
                .filter(parkingLot -> parkingLot.getCapacity() > 0)
                .findFirst();
    }
}
