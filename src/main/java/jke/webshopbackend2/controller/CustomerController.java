package jke.webshopbackend2.controller;

import jakarta.servlet.http.HttpSession;
import jke.webshopbackend2.dto.LoginRequest;
import jke.webshopbackend2.dto.RegisterRequest;
import jke.webshopbackend2.model.Customer;
import jke.webshopbackend2.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CustomerController {

    final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterRequest registerRequest, RedirectAttributes redirectAttributes, Model model) {
        final var result = customerService.register(registerRequest);
        System.out.println(result);
        if (result.getStatusCode().is2xxSuccessful()) {
            redirectAttributes.addFlashAttribute("success", "Registered successfully");
            return "redirect:/login";
        } else {
            model.addAttribute("error", "Something went wrong");
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    /*@PostMapping("/login")
    public String loginUser(@ModelAttribute LoginRequest loginRequest, RedirectAttributes redirectAttributes, HttpSession session) {
        ResponseEntity<?> response = customerService.login(loginRequest);
        System.out.println("response: " + response);
        if (response.getStatusCode().is2xxSuccessful()) {
            session.setAttribute("user", response.getBody());
            redirectAttributes.addFlashAttribute("success", "Välkommen " + ((Customer) session.getAttribute("user")).getUsername() + "!");
            System.out.println("hej! du är inloggad");
            return "redirect:/home";
        }

        redirectAttributes.addFlashAttribute("error", "Fel användarnamn eller lösenord");
        return "redirect:/login";
    }*/
}
