package com.cluna2.customer_website.exceptions;

public class CarAlreadyAssignedException extends RuntimeException {
    public CarAlreadyAssignedException(String message) {
        super(message);
    }
    public CarAlreadyAssignedException() {}
}
