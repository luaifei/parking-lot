package com.thoughtworks.aep.parkinglot;

import com.thoughtworks.aep.parkinglot.exception.NoEnoughParkingLotException;
import com.thoughtworks.aep.parkinglot.exception.PickCarFailureException;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

public class ParkingLot {

    @Getter
    private String name;
    @Getter
    private int size;
    @Getter
    private int capacity;
    private Map<Ticket, Car> parkingCars;

    public ParkingLot(int size) {
        this("initParkingName", size);
    }

    public ParkingLot(String name, int size) {
        this.name = name;
        this.size = size;
        this.capacity = size;
        this.parkingCars = new HashMap<>(size);
    }

    public Ticket park(Car car) {
        if (capacity < 1) {
            throw new NoEnoughParkingLotException();
        }
        capacity--;
        Ticket ticket = createTicket();
        parkingCars.put(ticket, car);

        return ticket;
    }

    public List<Ticket> park(List<Car> carList) {
        if (capacity < carList.size()) {
            throw new NoEnoughParkingLotException();
        }
        return carList.stream().map(this::park).collect(Collectors.toList());
    }

    public Car pick(Ticket ticket) {
        capacity ++;
        if (!parkingCars.containsKey(ticket)) {
            throw new PickCarFailureException();
        }
        Car car = parkingCars.get(ticket);
        parkingCars.remove(ticket);
        return car;
    }

    public double getCapacityRate() {
        return  this.capacity / (double) this.size;
    }

    private Ticket createTicket() {
        return new Ticket(this.name, UUID.randomUUID().toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingLot that = (ParkingLot) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
