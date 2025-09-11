package com.cluna2.customer_website.controllers;

import com.cluna2.customer_website.exceptions.CarAlreadyAssignedException;
import com.cluna2.customer_website.exceptions.NoSuchCarException;
import com.cluna2.customer_website.exceptions.NoSuchCustomerException;
import com.cluna2.customer_website.models.Car;
import com.cluna2.customer_website.models.Customer;
import com.cluna2.customer_website.services.CarService;
import com.cluna2.customer_website.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    private final CarService carService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        final List<Customer> customerList = customerService.getAllCustomers();
        model.addAttribute("customerList", customerList);
        return "index";
    }

    @GetMapping("/new")
    public String showNewCustomerPage(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        // return "new-customer" view
        return "new-customer";
    }

    @PostMapping("/save")
    public String saveCustomer(
            @ModelAttribute("customer") Customer customer, Model model) {
        try {
            customerService.saveCustomer(customer);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("message",
                    "Customer passed in is null: " + e.getMessage());
            return "error-page";
        }

    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditCustomerPage(
            @PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("edit-customer");
        Customer customer = customerService.getCustomer(id);
        mav.addObject("customer", customer);
        return mav;
    }

    @PostMapping("/update/{id}")
    public String updateCustomer(
            @PathVariable("id") Long id,
            @ModelAttribute("customer") Customer customer, Model model) {
        if (!id.equals(customer.getId())) {
            model.addAttribute("message",
                    "Cannot update, customer id " + customer.getId() +
                            " doesn't match id to update: " + id + ".");
            return "error-page";
        }
        customer = customerService.getCustomer(id);
        customerService.saveCustomer(customer);
        return "redirect:/";
    }

    @RequestMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Long id, Model model) {
        try {
            customerService.deleteCustomer(id);
            return "redirect:/";
        } catch (NoSuchCustomerException | CarAlreadyAssignedException e) {
            model.addAttribute("message",
                    "Cannot delete customer with ID: " + id +
                            " " + e.getMessage());
            return "error-page";
        }

    }

    @GetMapping("/cars/assign/{id}")
    public String showAssignPage(@PathVariable("id") Long id, Model model) {
        try {
            Customer customer = customerService.getCustomer(id);
            model.addAttribute("customer", customer);
            List<Car> carList = carService.getAllUnassignedCars();
            model.addAttribute("carList", carList);
            return "assign";
        } catch (NoSuchCustomerException | CarAlreadyAssignedException e) {
            model.addAttribute("message",
                    "Cannot assign to customer with ID: " + id +
                    " " + e.getMessage());
            return "error-page";
        }
    }

    @PostMapping("/cars/assign")
    public String assignCar(
            @ModelAttribute("customerId") Long customerId,
            @ModelAttribute("carId") Long carId,
            Model model) {
        try {
            Car car = carService.getCar(carId);
            customerService.assignCar(customerId, car);
            return "redirect:/";
        } catch (NoSuchCustomerException e) {
            model.addAttribute("message",
                    "Cannot assign to customer with ID: " + customerId +
                            " " + e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/remove/{id}")
    public String removeAssignedCar(
            @PathVariable("id") Long id,
            Model model) {
        try {
            customerService.removeCar(id);
            return "redirect:/";
        } catch (NoSuchCustomerException e) {
            model.addAttribute("message",
                    "Cannot remove car from customer with ID: " + id +
                            " " + e.getMessage());
            return "error-page";
        }
    }
}
