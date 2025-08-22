package jke.webshopbackend2.controller;

import jakarta.servlet.http.HttpSession;
import jke.webshopbackend2.dto.LoginRequest;
import jke.webshopbackend2.dto.RegisterRequest;
import jke.webshopbackend2.dto.UserDto;
import jke.webshopbackend2.service.UserService;
import jke.webshopbackend2.model.User;
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


    @PostMapping("/register")
    public String register(@ModelAttribute RegisterRequest registerRequest, RedirectAttributes redirectAttributes, Model model) {
        final var result = userService.register(registerRequest);
        if (result.getStatusCode().is2xxSuccessful()) {
            redirectAttributes.addFlashAttribute("success", "Registered successfully");
            return "redirect:/login";
        } else {
            model.addAttribute("error", result.getBody());
            return "register";
        }
    }

    @GetMapping("/login")
    public String login(@ModelAttribute("user") LoginRequest loginRequest, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        ResponseEntity<UserDto> response = userService.login(loginRequest);
        UserDto loggedInUser = response.getBody();

        if (loggedInUser == null) {
            redirectAttributes.addFlashAttribute("error", "Invalid username or password");
            return "redirect:/login";
        }

        session.setAttribute("loggedInUser", loggedInUser);

        if ("ADMIN".equalsIgnoreCase(loggedInUser.role())) {
            return "redirect:/admin";
        } else {
            return "redirect:/user";
        }
    }
}