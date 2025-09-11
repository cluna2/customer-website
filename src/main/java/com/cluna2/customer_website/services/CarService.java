package com.cluna2.customer_website.services;

import com.cluna2.customer_website.models.Car;
import com.cluna2.customer_website.models.Customer;

import java.util.List;

public interface CarService {

    List<Car> getAllCars();

    Car saveCar(Car car);

    Car getCar(Long id);

    void deleteCar(Long id);

    List<Car> saveAllCars(List<Car> cars);

    List<Car> getAllUnassignedCars();
}
