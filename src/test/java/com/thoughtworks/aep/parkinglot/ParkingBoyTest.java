package com.thoughtworks.aep.parkinglot;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertNotNull;

public class ParkingBoyTest {
    @Test
    public void test_should_return_ticket_when_park_given_one_lot_with_one_capacity() {
        ParkingBoy parkingBoy = new ParkingBoy();
        ParkingLot parkingA = new ParkingLot("parkA",1);
        parkingBoy.setManagedParkingLots(Arrays.asList(parkingA));
        Ticket ticket = parkingBoy.park(new Car("A88888"));
        assertNotNull(ticket);
    }
}
