package com.cluna2.customer_website;

import com.cluna2.customer_website.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.cluna2.customer_website.models.Customer;

import java.util.Arrays;

@SpringBootApplication
public class CustomerWebsiteApplication implements CommandLineRunner {

	@Autowired
	private CustomerService customerService;

	public static void main(String[] args) {
		SpringApplication.run(CustomerWebsiteApplication.class, args);
	}

	@Override
	public void run(String... args) {
		if (customerService.getAllCustomers().isEmpty()) {
			customerService.saveAllCustomer(Arrays.asList(
					Customer.builder()
							.fullName("Customer 1")
							.emailAddress("customer1@gmail.com")
							.address("Customer Address 1")
							.age(30)
							.build(),
					Customer.builder()
							.fullName("Customer 2")
							.emailAddress("customer2@gmail.com")
							.address("Customer Address Two")
							.age(28)
							.build(),
					Customer.builder()
							.fullName("Customer 3")
							.emailAddress("customer3@gmail.com")
							.address("Customer Address Three")
							.age(32)
							.build()
			));
		}

	}
}
