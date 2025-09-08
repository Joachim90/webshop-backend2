package jke.webshopbackend2.controller;

import jke.webshopbackend2.model.Customer;
import jke.webshopbackend2.security.ConcreteUserDetails;
import jke.webshopbackend2.service.CustomerOrderService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public String purchaseProduct(@RequestParam("productId") int productId,
                                  RedirectAttributes redirectAttributes) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ConcreteUserDetails userDetails = (ConcreteUserDetails) auth.getPrincipal();
        Customer customer = userDetails.getCustomer();

        boolean success = customerOrderService.purchaseProduct(productId, customer);

        if (success) {
            redirectAttributes.addFlashAttribute("success", "Köpet lyckades!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Något gick fel, köpet kunde inte genomföras.");
        }

        return "redirect:/home";
    }

    @PostMapping("/delete-order")
    public String deleteOrder(@RequestParam("customerOrderId") int customerOrderId, RedirectAttributes redirectAttributes, Model model) {

        String result = customerOrderService.deleteOrder(customerOrderId);

        if (result.equals("success")) {
            redirectAttributes.addFlashAttribute("success", "Order borttagen!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Något gick fel, kunde inte ta bort ordern.");
        }
        return "redirect:/home";
    }

}
