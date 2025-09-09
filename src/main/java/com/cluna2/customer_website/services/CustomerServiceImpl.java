package com.cluna2.customer_website.services;

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
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        customer = customerRepo.save(customer);
        return customer;
    }

    @Override
    public Customer getCustomer(Long id) {
        return customerRepo.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteCustomer(Long id) {
        customerRepo.deleteById(id);
    }

    @Override
    @Transactional
    public List<Customer> saveAllCustomer(List<Customer> customerList) {
        return customerRepo.saveAll(customerList);
    }
}
