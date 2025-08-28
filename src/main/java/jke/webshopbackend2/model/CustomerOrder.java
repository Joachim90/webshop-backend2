package jke.webshopbackend2.model;


import jakarta.persistence.*;

@Entity
public class CustomerOrder {
    public CustomerOrder(Customer customer, Product product) {
        this.customer = customer;
        this.product = product;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Product product;

    public CustomerOrder() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
