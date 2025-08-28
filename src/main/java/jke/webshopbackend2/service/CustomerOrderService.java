package jke.webshopbackend2.service;

import jke.webshopbackend2.model.CustomerOrder;
import jke.webshopbackend2.model.Product;
import jke.webshopbackend2.model.Customer;
import jke.webshopbackend2.repository.CustomerOrderRepository;
import jke.webshopbackend2.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;
    private final ProductRepository productRepository;

    public CustomerOrderService(CustomerOrderRepository customerOrderRepository, ProductRepository productRepository) {
        this.customerOrderRepository = customerOrderRepository;
        this.productRepository = productRepository;
    }

    public List<CustomerOrder> getAllCustomerOrders() {
        return customerOrderRepository.findAll();
    }

    public boolean purchaseProduct(Integer productId, Customer customer) {
        if (customer == null) {
            throw new NullPointerException("User is null");
        }
        Product product = productRepository.findById(productId).orElse(null);

        if (product != null) {
            customerOrderRepository.save(new CustomerOrder(customer, product));
            return true;
        }

        return false;
    }

    public String deleteOrder(int customerOrderId){
        CustomerOrder customerOrder = customerOrderRepository.findById(customerOrderId).orElse(null);

        if (customerOrder != null) {
            customerOrderRepository.delete(customerOrder);
            return "success";
        }
        return "fail";
    }

}
