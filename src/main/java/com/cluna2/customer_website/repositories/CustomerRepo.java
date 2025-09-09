package com.cluna2.customer_website.repositories;

import com.cluna2.customer_website.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
}
