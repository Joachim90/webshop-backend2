package jke.webshopbackend2.controller;

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

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String showProducts(Model model) {
        List<Product> products = productService.getAllProducts();

        Product randomProduct = products.isEmpty() ? null : products.get((int) (Math.random() * products.size()));
        model.addAttribute("randomProduct", randomProduct);

        Map<String, List<Product>> productsByCategory = products.stream()
                .collect(Collectors.groupingBy(Product::getCategory));
        model.addAttribute("productsByCategory", productsByCategory);

        return "home";
    }
}
