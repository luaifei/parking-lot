package com.thoughtworks.aep.parkinglot;

import com.thoughtworks.aep.parkinglot.exception.NoEnoughParkingLotException;
import com.thoughtworks.aep.parkinglot.exception.PickCarFailureException;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

public class ParkingBoy {

    @Setter
    private List<ParkingLot> managedParkingLots;

    public Ticket park(Car car) {
        Optional.of(managedParkingLots).orElseThrow(NoEnoughParkingLotException::new);

        return managedParkingLots.stream()
                .filter(parkingLot -> parkingLot.getCapacity() > 0)
                .findFirst()
                .map(parkingLot -> parkingLot.park(car))
                .orElseThrow(NoEnoughParkingLotException::new);
    }

    public Car pick(Ticket ticket) {
        Optional.of(managedParkingLots).orElseThrow(PickCarFailureException::new);

        return managedParkingLots.stream()
                .filter(parkingLot -> ticket.getParkingLotName().equals(parkingLot.getName()))
                .findFirst()
                .map(parkingLot -> parkingLot.pick(ticket))
                .orElseThrow(PickCarFailureException::new);
    }
}
