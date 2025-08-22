package jke.webshopbackend2.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity
public class CustomerOrder {

    @Id
    @GeneratedValue
    public int id;

    @ManyToOne
    private User customer;
    public LocalDateTime orderDateTime;
}
