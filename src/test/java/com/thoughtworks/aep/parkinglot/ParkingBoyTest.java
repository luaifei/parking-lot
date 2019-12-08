package com.thoughtworks.aep.parkinglot;

import com.thoughtworks.aep.parkinglot.exception.NoEnoughParkingLotException;
import com.thoughtworks.aep.parkinglot.exception.PickCarFailureException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingBoyTest {
    @Test
    public void test_should_return_ticket_when_park_given_one_lot_with_one_capacity() {
        ParkingBoy parkingBoy = new ParkingBoy();
        ParkingLot parkingA = new ParkingLot("parkA",1);
        parkingBoy.setManagedParkingLots(Collections.singletonList(parkingA));
        Ticket ticket = parkingBoy.park(new Car("A88888"));
        assertNotNull(ticket);
    }

    @Test
    public void test_should_park_in_the_first_lot_when_park_two_cars_given_first_lots_with_enough_capacity() {
        ParkingBoy parkingBoy = new ParkingBoy();
        parkingBoy.setManagedParkingLots(
                Arrays.asList(new ParkingLot("firstPark",3), new ParkingLot("secondPark",3)));
        Ticket firstTicket = parkingBoy.park(new Car("A88888"));
        Ticket secondTicket = parkingBoy.park(new Car("A99999"));
        assertEquals("firstPark", firstTicket.getParkingLotName());
        assertEquals("firstPark", secondTicket.getParkingLotName());
    }

    @Test
    public void test_should_first_into_first_lot_and_next_into_second_when_park_given_first_lots_with_one_capacity() {
        ParkingBoy parkingBoy = new ParkingBoy();
        parkingBoy.setManagedParkingLots(
                Arrays.asList(new ParkingLot("firstPark",1), new ParkingLot("secondPark",3)));
        Ticket firstTicket = parkingBoy.park(new Car("A88888"));
        Ticket secondTicket = parkingBoy.park(new Car("A99999"));
        assertEquals("firstPark", firstTicket.getParkingLotName());
        assertEquals("secondPark", secondTicket.getParkingLotName());
    }

    @Test
    public void test_should_all_park_into_second_lot_when_park_given_first_lot_is_full_and_second_with_enough_capacity() {
        ParkingBoy parkingBoy = new ParkingBoy();
        parkingBoy.setManagedParkingLots(
                Arrays.asList(new ParkingLot("firstPark",0), new ParkingLot("secondPark",3)));
        Ticket firstTicket = parkingBoy.park(new Car("A88888"));
        Ticket secondTicket = parkingBoy.park(new Car("A99999"));
        assertEquals("secondPark", firstTicket.getParkingLotName());
        assertEquals("secondPark", secondTicket.getParkingLotName());
    }

    @Test
    public void test_should_raise_exception_when_park_given_two_lots_are_full() {
        ParkingBoy parkingBoy = new ParkingBoy();
        parkingBoy.setManagedParkingLots(
                Arrays.asList(new ParkingLot("firstPark",0), new ParkingLot("secondPark",0)));
        assertThrows(NoEnoughParkingLotException.class, () -> parkingBoy.park(new Car("A88888")));
    }

    @Test
    public void test_should_return_my_car_when_pick_given_valid_ticket_and_only_my_car_park_in_lots() {
        ParkingBoy parkingBoy = new ParkingBoy();
        parkingBoy.setManagedParkingLots(Collections.singletonList(new ParkingLot("firstPark", 1)));
        Car myCar = new Car("A88888");
        Ticket ticket = parkingBoy.park(myCar);
        Car car = parkingBoy.pick(ticket);
        assertEquals(myCar, car);
    }

    @Test
    public void test_should_return_my_car_when_pick_given_valid_ticket_and_many_cars_also_park_in_lots() {
        ParkingBoy parkingBoy = new ParkingBoy();
        parkingBoy.setManagedParkingLots(
                Arrays.asList(new ParkingLot("firstPark",1),
                        new ParkingLot("secondPark",1)));
        Car myCar = new Car("A88888");
        Ticket ticket = parkingBoy.park(myCar);
        parkingBoy.park(new Car("A99999"));
        assertEquals(myCar, parkingBoy.pick(ticket));
    }

    @Test
    public void test_should_raise_exception_when_pick_given_invalid_ticket() {
        ParkingBoy parkingBoy = new ParkingBoy();
        parkingBoy.setManagedParkingLots(
                Arrays.asList(new ParkingLot("firstPark",1),
                        new ParkingLot("secondPark",1)));
        Car myCar = new Car("A88888");
        Ticket ticket = parkingBoy.park(myCar);
        assertThrows(PickCarFailureException.class,
                () -> parkingBoy.pick(new Ticket("InvalidParkingLotName", ticket.getIdentityNo())));
        assertThrows(PickCarFailureException.class,
                () -> parkingBoy.pick(new Ticket(ticket.getParkingLotName(), "InvalidID")));
        assertThrows(PickCarFailureException.class,
                () -> parkingBoy.pick(new Ticket("InvalidParkingLotName", "InvalidID")));
    }

    @Test
    public void test_should_raise_exception_when_pick_twice_given_same_valid_ticket() {
        ParkingBoy parkingBoy = new ParkingBoy();
        parkingBoy.setManagedParkingLots(
                Arrays.asList(new ParkingLot("firstPark",1),
                        new ParkingLot("secondPark",1)));
        Car myCar = new Car("A88888");
        Ticket ticket = parkingBoy.park(myCar);
        parkingBoy.pick(ticket);
        assertThrows(PickCarFailureException.class, () -> parkingBoy.pick(ticket));
    }

}
