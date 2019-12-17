package com.thoughtworks.aep.parkinglot;

import com.thoughtworks.aep.parkinglot.exception.NoEnoughParkingLotException;
import com.thoughtworks.aep.parkinglot.exception.PickCarFailureException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SmartParkingBoyTest {
    @Test
    public void test_should_return_ticket_when_park_given_one_lot_with_one_capacity() {
        ParkingLot parkingA = new ParkingLot("parkA",1);
        ParkingBoy parkingBoy = new SmartParkingBoy(Collections.singletonList(parkingA));

        Ticket ticket = parkingBoy.park(new Car("A88888"));

        assertNotNull(ticket);
    }

    @Test
    public void test_should_park_into_first_lots_when_park_given_two_lots_and_first_capacity_more_than_second() {
        ParkingLot firstPark = new ParkingLot("firstPark", 3);
        ParkingLot secondPark = new ParkingLot("secondPark", 1);
        List<ParkingLot> parkingLots = Arrays.asList(firstPark, secondPark);
        ParkingBoy parkingBoy = new SmartParkingBoy(parkingLots);

        Car myCar = new Car("A88888");
        Ticket ticket = parkingBoy.park(myCar);

        assertEquals(myCar, firstPark.pick(ticket));
    }

    @Test
    public void test_should_park_into_second_lots_when_park_given_two_lots_and_second_capacity_more_than_first() {
        ParkingLot firstPark = new ParkingLot("firstPark", 1);
        ParkingLot secondPark = new ParkingLot("secondPark", 3);
        List<ParkingLot> parkingLots = Arrays.asList(firstPark, secondPark);
        ParkingBoy parkingBoy = new SmartParkingBoy(parkingLots);

        Car myCar = new Car("A88888");
        Ticket ticket = parkingBoy.park(myCar);

        assertEquals(myCar, secondPark.pick(ticket));
    }

    @Test
    public void test_should_alternative_park_into_lots_when_park_given_two_lots_with_same_capacity() {
        ParkingLot firstPark = new ParkingLot("firstPark", 3);
        ParkingLot secondPark = new ParkingLot("secondPark", 3);
        List<ParkingLot> parkingLots = Arrays.asList(firstPark, secondPark);
        ParkingBoy parkingBoy = new SmartParkingBoy(parkingLots);

        Car car1 = new Car("A88888");
        Car car2 = new Car("A99999");
        Car car3 = new Car("A777777");

        Ticket ticket1 = parkingBoy.park(car1);
        Ticket ticket2 = parkingBoy.park(car2);
        Ticket ticket3 = parkingBoy.park(car3);

        assertEquals(car1, firstPark.pick(ticket1));
        assertEquals(car2, secondPark.pick(ticket2));
        assertEquals(car3, firstPark.pick(ticket3));
    }

    @Test
    public void test_should_raise_exception_when_park_given_two_lots_both_full() {
        ParkingLot firstPark = new ParkingLot("firstPark", 0);
        ParkingLot secondPark = new ParkingLot("secondPark", 0);
        List<ParkingLot> parkingLots = Arrays.asList(firstPark, secondPark);
        ParkingBoy parkingBoy = new SmartParkingBoy(parkingLots);

        assertThrows(NoEnoughParkingLotException.class, () -> parkingBoy.park(new Car("A88888")));
    }

    @Test
    public void test_should_return_my_car_when_pick_given_only_my_car_park_in_lot() {
        ParkingLot firstPark = new ParkingLot("firstPark", 3);
        ParkingLot secondPark = new ParkingLot("secondPark", 3);
        List<ParkingLot> parkingLots = Arrays.asList(firstPark, secondPark);
        ParkingBoy parkingBoy = new SmartParkingBoy(parkingLots);

        Car myCar = new Car("A88888");
        Ticket ticket = parkingBoy.park(myCar);

        assertEquals(myCar, parkingBoy.pick(ticket));
    }

    @Test
    public void test_should_return_my_car_when_pick_given_many_other_cars_park_in_lot() {
        ParkingLot firstPark = new ParkingLot("firstPark", 3);
        ParkingLot secondPark = new ParkingLot("secondPark", 3);
        List<ParkingLot> parkingLots = Arrays.asList(firstPark, secondPark);
        ParkingBoy parkingBoy = new SmartParkingBoy(parkingLots);

        Car myCar = new Car("A88888");
        Ticket ticket = parkingBoy.park(myCar);
        parkingBoy.park(new Car("A9999"));

        assertEquals(myCar, parkingBoy.pick(ticket));
    }

    @Test
    public void test_should_raise_exception_when_pick_given_invalid_ticket() {
        ParkingLot firstPark = new ParkingLot("firstPark", 1);
        ParkingLot secondPark = new ParkingLot("secondPark", 1);
        ParkingBoy parkingBoy = new SmartParkingBoy(Arrays.asList(firstPark, secondPark));

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
        ParkingLot firstPark = new ParkingLot("firstPark", 1);
        ParkingLot secondPark = new ParkingLot("secondPark", 1);
        ParkingBoy parkingBoy = new SmartParkingBoy(Arrays.asList(firstPark, secondPark));

        Car myCar = new Car("A88888");
        Ticket ticket = parkingBoy.park(myCar);

        parkingBoy.pick(ticket);
        assertThrows(PickCarFailureException.class, () -> parkingBoy.pick(ticket));
    }
}
