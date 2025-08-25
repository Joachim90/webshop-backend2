package jke.webshopbackend2.controller;

import jakarta.servlet.http.HttpSession;
import jke.webshopbackend2.model.User;
import jke.webshopbackend2.service.CustomerOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CustomerOrderController {

    final CustomerOrderService customerOrderService;

    public CustomerOrderController(CustomerOrderService customerOrderService) {
        this.customerOrderService = customerOrderService;
    }

    @PostMapping("/purchase")
    public String purchaseProduct(@RequestParam("productId") int productId, HttpSession session, RedirectAttributes redirectAttributes) {
        //UserDto user = (UserDto) session.getAttribute("user");
        User user = (User) session.getAttribute("user");
        final String result = customerOrderService.purchaseProduct(productId, user);

        return "home";
    }
}
