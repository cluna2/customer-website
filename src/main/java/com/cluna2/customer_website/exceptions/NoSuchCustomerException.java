package com.cluna2.customer_website.exceptions;

public class NoSuchCustomerException extends RuntimeException {
    public NoSuchCustomerException(String message) {
        super(message);
    }
    public NoSuchCustomerException() {}
}
