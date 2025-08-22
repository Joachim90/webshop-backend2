package jke.webshopbackend2.controller;

import jakarta.servlet.http.HttpSession;
import jke.webshopbackend2.dto.LoginRequest;
import jke.webshopbackend2.dto.RegisterRequest;
import jke.webshopbackend2.dto.UserDto;
import jke.webshopbackend2.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping
    public String register(@ModelAttribute RegisterRequest registerRequest, RedirectAttributes redirectAttributes, Model model) {
        final var result = userService.register(registerRequest);
        if (result.equals(ResponseEntity.ok().build())) {
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

    @PostMapping("/login")
    public String loginUser(@ModelAttribute LoginRequest loginRequest, RedirectAttributes redirectAttributes, HttpSession session) {
        ResponseEntity<?> response = userService.login(loginRequest);

        if (response.getStatusCode().is2xxSuccessful()) {
            UserDto user = new UserDto(loginRequest.name(), null);
            session.setAttribute("user", user);
            redirectAttributes.addFlashAttribute("success", "Välkommen " + user.name() + "!");
            return "redirect:/start";
        }

        redirectAttributes.addFlashAttribute("error", "Fel användarnamn eller lösenord");
        return "redirect:/login";
    }
}
