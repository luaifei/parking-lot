package com.thoughtworks.aep.parkinglot.exception;

public class PickCarFailureException extends RuntimeException {
    public PickCarFailureException() {
        super("取车失败，请确认Ticket是否正确！");
    }
}
