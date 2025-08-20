package jke.webshopbackend2.model;

public class Product{
    public int id;
    public String title;
    public double price;
    public String description;
    public String category;
    public String image;
    public Rating rating;
}

class Rating{
    public double rate;
    public int count;
}


