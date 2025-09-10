package com.cluna2.customer_website.controllers;

import com.cluna2.customer_website.models.Customer;
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

    @GetMapping("/save")
    public String saveCustomer(
            @ModelAttribute("customer") Customer customer) {
        customerService.saveCustomer(customer);
        return "redirect:/";
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
        customerService.saveCustomer(customer);
        return "redirect:/";
    }

    @RequestMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/";
    }
}
