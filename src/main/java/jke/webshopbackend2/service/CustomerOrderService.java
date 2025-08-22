package jke.webshopbackend2.service;


import jke.webshopbackend2.model.CustomerOrder;
import jke.webshopbackend2.model.Product;
import jke.webshopbackend2.model.User;
import jke.webshopbackend2.repository.CustomerOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;

    public CustomerOrderService(CustomerOrderRepository customerOrderRepository) {
        this.customerOrderRepository = customerOrderRepository;
    }

    public List<CustomerOrder> getAllCustomerOrders() {
        return customerOrderRepository.findAll();
    }

    //TODO: dto bör användas här, userToDto / dtoToUser metoder behövs
    public String purchaseProduct(Integer productId, User user) {
        CustomerOrder customerOrder = new CustomerOrder(user,productId);

        customerOrderRepository.save(customerOrder);
        return "success";
    }

}
