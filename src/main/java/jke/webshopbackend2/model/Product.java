package jke.webshopbackend2.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Product {
    @Id
    public int id;
    public String title;
    public double price;
    public String description;
    public String category;
    public String image;
    @Embedded
    private Rating rating;
}

class Rating {
    public double rate;
    public int count;
}


