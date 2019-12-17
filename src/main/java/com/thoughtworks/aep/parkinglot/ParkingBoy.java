package com.thoughtworks.aep.parkinglot;

public interface ParkingBoy {

    Ticket park(Car car);

    Car pick(Ticket ticket);
}
