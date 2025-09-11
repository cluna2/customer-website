package com.cluna2.customer_website.services;

import com.cluna2.customer_website.exceptions.CarAlreadyAssignedException;
import com.cluna2.customer_website.exceptions.NoSuchCarException;
import com.cluna2.customer_website.models.Car;
import com.cluna2.customer_website.repositories.CarRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CarServiceImpl implements CarService {

    private final CarRepo carRepo;


    @Override
    public List<Car> getAllCars(){
        return carRepo.findAll();
    }

    @Override
    @Transactional
    public Car saveCar(Car car) throws IllegalArgumentException {
        if (car == null) {
            throw new IllegalArgumentException("Car must not be null");
        }
        car = carRepo.save(car);
        return car;
    }

    @Override
    public Car getCar(Long id) throws NoSuchCarException {
        Optional<Car> carOptional = carRepo.findById(id);
        if (carOptional.isEmpty()) {
            throw new NoSuchCarException("Car with ID: " + id +
                    " could not be found.");
        }
        return carOptional.get();
    }

    @Override
    @Transactional
    public void deleteCar(Long id) throws NoSuchCarException{
        try {
            Car car = getCar(id);
            if (car.getCustomer() != null) {
                throw new CarAlreadyAssignedException("Car with ID: " +
                        id + " is currently assigned to customer with ID: " + car.getCustomer().getId() +
                        ". Please unassign this car from the customer first.");
            }
            carRepo.delete(car);
        } catch (NoSuchCarException e) {
            throw new NoSuchCarException("Could not delete car: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<Car> saveAllCars(List<Car> cars) throws IllegalArgumentException {
        if (cars == null || cars.isEmpty()) {
            throw new IllegalArgumentException("Car list must not be empty");
        }
        return carRepo.saveAll(cars);
    }

    @Override
    public List<Car> getAllUnassignedCars() {
        return carRepo.findByCustomerIsNull();
    }
}
