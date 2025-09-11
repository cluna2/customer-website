package com.cluna2.customer_website.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")
@Builder
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    private String fullName;
    private String emailAddress;
    private Integer age;
    private String address;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;
}
