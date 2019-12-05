package com.thoughtworks.aep.parkinglot.exception;

public class NoEnoughParkingLotException extends RuntimeException {
    public NoEnoughParkingLotException() {
        super("停车位不足！");
    }
}
