package com.cluna2.customer_website.controllers;

import com.cluna2.customer_website.exceptions.CarAlreadyAssignedException;
import com.cluna2.customer_website.exceptions.NoSuchCarException;
import com.cluna2.customer_website.models.Car;
import com.cluna2.customer_website.services.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("/cars")
    public String viewCars(Model model) {
        final List<Car> cars = carService.getAllCars();
        model.addAttribute("carList", cars);
        return "cars";
    }

    @GetMapping("/new-car")
    public String showNewCarPage(Model model) {
        Car car = new Car();
        model.addAttribute("car", car);
        return "new-car";
    }

    @PostMapping("/cars")
    public String saveCar(
            @ModelAttribute("car") Car car, Model model) {
        try {
            Car savedCar = carService.saveCar(car);
            carService.getCar(savedCar.getId());
            return "redirect:/cars";
        } catch (IllegalArgumentException e) {
            model.addAttribute("message",
                    "Car passed in is null: " + e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("edit-car/{id}")
    public String showEditCarPage(@PathVariable("id") Long id, Model model) {
        try {
            Car car = carService.getCar(id);
            model.addAttribute("car", car);
        } catch (NoSuchCarException e) {
            model.addAttribute("message",
                    "Car not found: " + e.getMessage());
            return "error-page";
        }
        return "edit-car";
    }

    @PostMapping("/update-car/{id}")
    public String updateCar(
            @PathVariable("id") Long id,
            @ModelAttribute("car") Car car,
            Model model) {

        if (!id.equals(car.getId())) {
            model.addAttribute("message",
                    "Cannot update, car id " + car.getId() +
                            " doesn't match id to update: " + id + ".");
            return "error-page";
        }
        carService.saveCar(car);
        return "redirect:/cars";
    }

    @RequestMapping("/delete-car/{id}")
    public String deleteCar(@PathVariable("id") Long id, Model model) {
        try {
            carService.deleteCar(id);
        } catch (NoSuchCarException | CarAlreadyAssignedException e) {
            model.addAttribute("message",
                    "Could not delete car: " + e.getMessage());
            return "error-page";
        }
        return "redirect:/cars";
    }

}
