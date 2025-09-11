package com.cluna2.customer_website.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue
    private Long id;

    private String color;
    private String make;
    private String model;
    private String license;

    @OneToOne(mappedBy = "car")
    private Customer customer;

    @Override
    public String toString() {return (color + " " + make + " " + model + " " + license);}
}
