package com.cluna2.customer_website.services;

import com.cluna2.customer_website.models.Car;
import com.cluna2.customer_website.models.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getAllCustomers();

    Customer saveCustomer(Customer customer);

    Customer getCustomer(Long id);

    void deleteCustomer(Long id);

    List<Customer> saveAllCustomer(List<Customer> customerList);

    Customer assignCar(Long customerId, Car car);

    Customer removeCar(Long id);
}
