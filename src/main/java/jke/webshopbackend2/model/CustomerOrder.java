package jke.webshopbackend2.model;


import jakarta.persistence.*;



@Entity
public class CustomerOrder {
    public CustomerOrder(User customer, Product product) {
        this.customer = customer;
        this.product = product;
    }

    @Id
    @GeneratedValue
    public int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User customer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public CustomerOrder() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
