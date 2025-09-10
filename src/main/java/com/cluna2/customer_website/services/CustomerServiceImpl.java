package com.cluna2.customer_website.services;

import com.cluna2.customer_website.exceptions.NoSuchCustomerException;
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
    public List<Customer> getAllCustomers() throws NoSuchCustomerException {

        List<Customer> customers = customerRepo.findAll();
        if (customers.isEmpty()) {
            throw new NoSuchCustomerException("No customers exist.");
        }
        return customers;
    }

    @Override
    public Customer saveCustomer(Customer customer) throws IllegalArgumentException {
        if (customer == null) {
            throw new IllegalArgumentException("Customer must not be null.");
        }
        customer = customerRepo.save(customer);
        return customer;
    }

    @Override
    public Customer getCustomer(Long id) throws NoSuchCustomerException {
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
        try {
            Customer customer = getCustomer(id);
            customerRepo.deleteById(id);
        } catch (NoSuchCustomerException e) {
            throw new NoSuchCustomerException("Could not delete: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<Customer> saveAllCustomer(List<Customer> customerList) throws IllegalArgumentException {
        if (customerList == null || customerList.isEmpty()) {
            throw new IllegalArgumentException("List of customers must not be empty.");
        }
        return customerRepo.saveAll(customerList);
    }
}
