package jke.webshopbackend2.controller;

import jakarta.validation.Valid;
import jke.webshopbackend2.dto.RegisterRequest;
import jke.webshopbackend2.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String register(@ModelAttribute @Valid RegisterRequest registerRequest, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", bindingResult.getAllErrors().getFirst().getDefaultMessage());
            return "register";
        }
        final var result = customerService.register(registerRequest);
        System.out.println(result);
        if (result.getStatusCode().is2xxSuccessful()) {
            redirectAttributes.addFlashAttribute("success", "Registreringen lyckades!");
            return "redirect:/login";
        } else {
            model.addAttribute("error", result.getBody());
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

}
