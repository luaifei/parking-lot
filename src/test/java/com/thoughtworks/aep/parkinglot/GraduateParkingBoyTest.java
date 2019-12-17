package com.thoughtworks.aep.parkinglot;

import com.thoughtworks.aep.parkinglot.exception.NoEnoughParkingLotException;
import com.thoughtworks.aep.parkinglot.exception.PickCarFailureException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GraduateParkingBoyTest {
    @Test
    public void test_should_return_ticket_when_park_given_one_lot_with_one_capacity() {
        ParkingLot parkingA = new ParkingLot("parkA",1);
        GraduateParkingBoy parkingBoy = new GraduateParkingBoy(Collections.singletonList(parkingA));

        Ticket ticket = parkingBoy.park(new Car("A88888"));

        assertNotNull(ticket);
    }

    @Test
    public void test_should_park_in_the_first_lot_when_park_two_cars_given_first_lots_with_enough_capacity() {
        ParkingLot firstPark = new ParkingLot("firstPark", 3);
        ParkingLot secondPark = new ParkingLot("secondPark", 3);
        List<ParkingLot> parkingLots = Arrays.asList(firstPark, secondPark);
        GraduateParkingBoy parkingBoy = new GraduateParkingBoy(parkingLots);

        Car car1 = new Car("A88888");
        Ticket firstTicket = parkingBoy.park(car1);
        Car car2 = new Car("A99999");
        Ticket secondTicket = parkingBoy.park(car2);

        assertEquals(car1, firstPark.pick(firstTicket));
        assertEquals(car2, firstPark.pick(secondTicket));
    }

    @Test
    public void test_should_first_into_first_lot_and_next_into_second_when_park_given_first_lots_with_one_capacity() {
        ParkingLot firstPark = new ParkingLot("firstPark", 1);
        ParkingLot secondPark = new ParkingLot("secondPark", 3);
        GraduateParkingBoy parkingBoy = new GraduateParkingBoy(Arrays.asList(firstPark, secondPark));

        Car car1 = new Car("A88888");
        Ticket firstTicket = parkingBoy.park(car1);
        Car car2 = new Car("A99999");
        Ticket secondTicket = parkingBoy.park(car2);

        assertEquals(car1, firstPark.pick(firstTicket));
        assertEquals(car2, secondPark.pick(secondTicket));
    }

    @Test
    public void test_should_all_park_into_second_lot_when_park_given_first_lot_is_full_and_second_with_enough_capacity() {
        ParkingLot firstPark = new ParkingLot("firstPark", 0);
        ParkingLot secondPark = new ParkingLot("secondPark", 3);
        GraduateParkingBoy parkingBoy = new GraduateParkingBoy(Arrays.asList(firstPark, secondPark));

        Car car1 = new Car("A88888");
        Ticket firstTicket = parkingBoy.park(car1);
        Car car2 = new Car("A99999");
        Ticket secondTicket = parkingBoy.park(car2);

        assertEquals(car1, secondPark.pick(firstTicket));
        assertEquals(car2, secondPark.pick(secondTicket));
    }

    @Test
    public void test_should_raise_exception_when_park_given_two_lots_are_full() {
        ParkingLot firstPark = new ParkingLot("firstPark", 0);
        ParkingLot secondPark = new ParkingLot("secondPark", 0);
        GraduateParkingBoy parkingBoy = new GraduateParkingBoy(Arrays.asList(firstPark, secondPark));

        assertThrows(NoEnoughParkingLotException.class, () -> parkingBoy.park(new Car("A88888")));
    }

    @Test
    public void test_should_return_my_car_when_pick_given_valid_ticket_and_only_my_car_park_in_lots() {
        ParkingLot firstPark = new ParkingLot("firstPark", 1);
        GraduateParkingBoy parkingBoy = new GraduateParkingBoy(Collections.singletonList(firstPark));

        Car myCar = new Car("A88888");
        Ticket ticket = parkingBoy.park(myCar);

        assertEquals(myCar, parkingBoy.pick(ticket));
    }

    @Test
    public void test_should_return_my_car_when_pick_given_valid_ticket_and_many_cars_also_park_in_lots() {
        ParkingLot firstPark = new ParkingLot("firstPark", 1);
        ParkingLot secondPark = new ParkingLot("secondPark", 1);
        GraduateParkingBoy parkingBoy = new GraduateParkingBoy(Arrays.asList(firstPark, secondPark));

        Car myCar = new Car("A88888");
        Ticket ticket = parkingBoy.park(myCar);
        parkingBoy.park(new Car("A99999"));

        assertEquals(myCar, parkingBoy.pick(ticket));
    }

    @Test
    public void test_should_raise_exception_when_pick_given_invalid_ticket() {
        ParkingLot firstPark = new ParkingLot("firstPark", 1);
        ParkingLot secondPark = new ParkingLot("secondPark", 1);
        GraduateParkingBoy parkingBoy = new GraduateParkingBoy(Arrays.asList(firstPark, secondPark));

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
        GraduateParkingBoy parkingBoy = new GraduateParkingBoy(Arrays.asList(firstPark, secondPark));

        Car myCar = new Car("A88888");
        Ticket ticket = parkingBoy.park(myCar);

        parkingBoy.pick(ticket);
        assertThrows(PickCarFailureException.class, () -> parkingBoy.pick(ticket));
    }

}
