package com.thoughtworks.aep.parkinglot;

import com.thoughtworks.aep.parkinglot.exception.NoEnoughParkingLotException;
import com.thoughtworks.aep.parkinglot.exception.PickCarFailureException;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public abstract class ParkingBoyTemplate implements ParkingBoy {

    protected List<ParkingLot> managedParkingLots;

    public ParkingBoyTemplate(@NonNull List<ParkingLot> managedParkingLots) {
        this.managedParkingLots = managedParkingLots;
    }

    @Override
    public Ticket park(Car car) {
        return selectParkingLot().map(parkingLot -> parkingLot.park(car))
                .orElseThrow(NoEnoughParkingLotException::new);
    }

    protected abstract Optional<ParkingLot> selectParkingLot();


    @Override
    public Car pick(Ticket ticket) {
        return managedParkingLots.stream()
                .filter(parkingLot -> ticket.getParkingLotName().equals(parkingLot.getName()))
                .findFirst()
                .map(parkingLot -> parkingLot.pick(ticket))
                .orElseThrow(PickCarFailureException::new);
    }
}
