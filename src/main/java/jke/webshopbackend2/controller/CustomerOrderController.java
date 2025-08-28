package jke.webshopbackend2.controller;

import jakarta.servlet.http.HttpSession;
import jke.webshopbackend2.model.Customer;
import jke.webshopbackend2.service.CustomerOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CustomerOrderController {

    final CustomerOrderService customerOrderService;

    public CustomerOrderController(CustomerOrderService customerOrderService) {
        this.customerOrderService = customerOrderService;
    }



    @PostMapping("/purchase")
    public String purchaseProduct(@RequestParam("productId") int productId, HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        Customer customer = (Customer) session.getAttribute("user");
        final var success = customerOrderService.purchaseProduct(productId, customer);

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

/*    @ModelAttribute("customerOrders")
    public List<CustomerOrder> allOrders(Model model) {
        List<CustomerOrder> customerOrders = customerOrderService.getAllCustomerOrders();
        model.addAttribute("customerOrders", customerOrders);

        if (customerOrders.isEmpty()){
            model.addAttribute("error", "Inga beställningar finns.");
        }
        System.out.println("Customer orders: " + customerOrders.size());
        return customerOrders;
    }*/
}
