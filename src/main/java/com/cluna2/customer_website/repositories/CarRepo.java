package com.cluna2.customer_website.repositories;

import com.cluna2.customer_website.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepo extends JpaRepository<Car, Long> {

    List<Car> findByCustomerIsNull();
}
