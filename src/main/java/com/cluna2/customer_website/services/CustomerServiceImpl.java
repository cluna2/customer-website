package com.cluna2.customer_website.services;

import com.cluna2.customer_website.exceptions.CarAlreadyAssignedException;
import com.cluna2.customer_website.exceptions.NoSuchCarException;
import com.cluna2.customer_website.exceptions.NoSuchCustomerException;
import com.cluna2.customer_website.models.Car;
import com.cluna2.customer_website.models.Customer;
import com.cluna2.customer_website.repositories.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepo customerRepo;


    @Override
    public List<Customer> getAllCustomers(){
        return customerRepo.findAll();
    }

    @Override
    @Transactional
    public Customer saveCustomer(Customer customer) throws IllegalArgumentException {
        if (customer == null) {
            throw new IllegalArgumentException("Customer must not be null.");
        }
        customer = customerRepo.save(customer);
        return customer;
    }

    @Override
    public Customer getCustomer(Long id) throws NoSuchCustomerException {
        if (id == null) {
            throw new NoSuchCustomerException("Customer ID is null.");
        }
        Optional<Customer> customerOptional = customerRepo.findById(id);
        if (customerOptional.isEmpty()) {
            throw new NoSuchCustomerException("Customer with ID: " + id +
                    "could not be found.");
        }
        return customerOptional.get();
    }

    @Override
    @Transactional
    public void deleteCustomer(Long id) throws NoSuchCustomerException {
        Customer customer = getCustomer(id);
        if (customer.getCar() != null) {
            throw new CarAlreadyAssignedException("Cannot delete customer that is assigned to a car.");
        }
        customerRepo.delete(customer);
    }

    @Override
    @Transactional
    public List<Customer> saveAllCustomer(List<Customer> customerList) throws IllegalArgumentException {
        if (customerList == null || customerList.isEmpty()) {
            throw new IllegalArgumentException("List of customers must not be empty.");
        }
        return customerRepo.saveAll(customerList);
    }

    @Override
    @Transactional
    public Customer assignCar(Long customerId, Car car)
            throws NoSuchCustomerException, CarAlreadyAssignedException, NoSuchCarException{
        if (car == null) {
            throw new NoSuchCarException("Car passed in is null.");
        }
        if (car.getCustomer() != null) {
            throw new CarAlreadyAssignedException("Car with ID: " +
                    car.getId() + " is already assigned to a different customer.");
        }
        Customer customer = getCustomer(customerId);
        customer.setCar(car);
        customer = saveCustomer(customer);
        return customer;
    }

    @Override
    @Transactional
    public Customer removeCar(Long id) throws NoSuchCustomerException {
        if (id == null) {
            throw new NoSuchCustomerException("Id must not be null.");
        }
        Customer customer = getCustomer(id);
        if (customer.getCar() != null) {
            customer.setCar(null);
        }
        return customerRepo.save(customer);
    }
}
