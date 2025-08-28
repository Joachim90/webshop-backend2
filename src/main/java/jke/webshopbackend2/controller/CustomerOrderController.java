package jke.webshopbackend2.controller;

import jakarta.servlet.http.HttpSession;
import jke.webshopbackend2.model.Customer;
import jke.webshopbackend2.service.CustomerOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String purchaseProduct(@RequestParam("productId") int productId, HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        System.out.println("här e vi");
        Customer customer = (Customer) session.getAttribute("user");
        final var success = customerOrderService.purchaseProduct(productId, customer);

        if (success) {
            redirectAttributes.addFlashAttribute("success", "Purchase successful");
        } else {
            redirectAttributes.addFlashAttribute("error", "Something went wrong");
        }
        return "redirect:/home";
    }

    @PostMapping("/delete-order")
    public String deleteOrder(@RequestParam("productId") int customerOrderId, RedirectAttributes redirectAttributes, Model model) {
        String result = customerOrderService.deleteOrder(customerOrderId);

        if (result.equals("success")) {
            model.addAttribute("success", "Purchase successful");
        } else {
            model.addAttribute("error", "Something went wrong");
        }
        return "home";
    }
}
