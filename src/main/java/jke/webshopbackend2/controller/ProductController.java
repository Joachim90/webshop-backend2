package jke.webshopbackend2.controller;

import jakarta.servlet.http.HttpServletRequest;
import jke.webshopbackend2.model.CustomerOrder;
import jke.webshopbackend2.service.CustomerOrderService;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.ui.Model;
import jke.webshopbackend2.model.Product;
import jke.webshopbackend2.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    final ProductService productService;
    final CustomerOrderService customerOrderService;

    public ProductController(ProductService productService, CustomerOrderService customerOrderService) {
        this.productService = productService;
        this.customerOrderService = customerOrderService;
    }

    @GetMapping("/home")
    public String showProductsAndOrders(Model model) {

        List<Product> products = productService.getAllProducts().stream().filter(Product::isActive).toList();

        Product randomProduct = products.isEmpty() ? null : products.get((int) (Math.random() * products.size()));
        model.addAttribute("randomProduct", randomProduct);

        Map<String, List<Product>> productsByCategory = products.stream()
                .collect(Collectors.groupingBy(Product::getCategory));
        model.addAttribute("productsByCategory", productsByCategory);

        List<CustomerOrder> customerOrders = customerOrderService.getAllCustomerOrders();
        model.addAttribute("customerOrders", customerOrders);

        return "home";
    }
}