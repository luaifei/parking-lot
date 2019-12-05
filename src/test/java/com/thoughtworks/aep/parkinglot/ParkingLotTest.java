package com.thoughtworks.aep.parkinglot;

import com.thoughtworks.aep.parkinglot.exception.NoEnoughParkingLotException;
import com.thoughtworks.aep.parkinglot.exception.PickCarFailureException;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ParkingLotTest {

    @Test
    public void test_should_return_ticket_when_park_one_car_given_one_available_capacity() {
        ParkingLot parkingLot = new ParkingLot(1);
        Car car = new Car("鄂A888888");
        Ticket ticket = parkingLot.park(car);
        assertNotNull(ticket);
    }

    @Test
    public void test_should_return_multiple_tickets_when_park_multiple_cars_given_has_enough_capacity() {
        ParkingLot parkingLot = new ParkingLot(10);
        List<Car> carList = Arrays.asList(new Car("鄂A888888"), new Car("鄂A999999"));
        List<Ticket> ticketList = parkingLot.park(carList);
        assertEquals(2, ticketList.size());
    }

    @Test(expected = NoEnoughParkingLotException.class)
    public void test_should_raise_exception_when_park_one_car_given_no_available_capacity() {
        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.park(new Car("鄂A888888"));
        parkingLot.park(new Car("鄂A999999"));
    }

    @Test
    public void test_should_return_car_when_pick_given_valid_ticket_and_only_park_my_car() {
        ParkingLot parkingLot = new ParkingLot(1);
        Car car = new Car("鄂A888888");
        Ticket ticket = parkingLot.park(car);
        Car pickedCar = parkingLot.pick(ticket);
        assertEquals(car, pickedCar);
    }

    @Test
    public void test_Should_return_car_when_pick_given_valid_ticket_and_park_many_cars() {
        ParkingLot parkingLot = new ParkingLot(2);
        Car myCar = new Car("鄂A888888");
        Car car2 = new Car("鄂A999999");
        Ticket ticket = parkingLot.park(myCar);
        parkingLot.park(car2);
        Car pickedCar = parkingLot.pick(ticket);
        assertEquals(myCar, pickedCar);
    }

    @Test(expected = PickCarFailureException.class)
    public void test_should_raise_exception_when_pick_given_invalid_ticket_and_my_car_in_lot() {
        ParkingLot parkingLot = new ParkingLot(2);
        Car myCar = new Car("鄂A888888");
        parkingLot.park(myCar);
        parkingLot.pick(new Ticket(parkingLot.getName(),"Invalid Ticket"));
    }

    @Test(expected = PickCarFailureException.class)
    public void test_should_raise_exception_when_pick_car_twice_given_same_valid_ticket() {
        ParkingLot parkingLot = new ParkingLot(2);
        Car myCar = new Car("鄂A888888");
        Ticket ticket = parkingLot.park(myCar);
        parkingLot.pick(ticket);
        parkingLot.pick(ticket);
    }
}
