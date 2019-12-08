package com.thoughtworks.aep.parkinglot;

import com.thoughtworks.aep.parkinglot.exception.NoEnoughParkingLotException;
import com.thoughtworks.aep.parkinglot.exception.PickCarFailureException;
import lombok.Setter;

import java.util.List;

public class ParkingBoy {

    @Setter
    private List<ParkingLot> managedParkingLots;

    public Ticket park(Car car) {
        if (managedParkingLots == null) {
            throw new NoEnoughParkingLotException();
        }

        return managedParkingLots.stream()
                .filter(parkingLot -> parkingLot.getCapacity() > 0)
                .findFirst()
                .map(parkingLot -> parkingLot.park(car))
                .orElseThrow(NoEnoughParkingLotException::new);
    }

    public Car pick(Ticket ticket) {
        if (managedParkingLots == null) {
            throw new PickCarFailureException();
        }
        return managedParkingLots.stream()
                .filter(parkingLot -> ticket.getParkingLotName().equals(parkingLot.getName()))
                .findFirst()
                .map(parkingLot -> parkingLot.pick(ticket))
                .orElseThrow(PickCarFailureException::new);
    }
}
