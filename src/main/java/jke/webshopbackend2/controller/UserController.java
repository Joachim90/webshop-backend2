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

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterRequest registerRequest, RedirectAttributes redirectAttributes, Model model) {
        final var result = userService.register(registerRequest);
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

    @PostMapping("/login")
    public String loginUser(@ModelAttribute LoginRequest loginRequest, RedirectAttributes redirectAttributes, HttpSession session, Model model) {
        ResponseEntity<?> response = userService.login(loginRequest);
        System.out.println("response: " + response);
        if (response.getStatusCode().is2xxSuccessful()) {
            UserDto user = new UserDto(loginRequest.username(), userService.findUserByUsername(loginRequest.username()).getRoles().stream().toList());
            session.setAttribute("user", user);
            redirectAttributes.addFlashAttribute("success", "Välkommen " + user.username() + "!");
            System.out.println("hej!");
            return "redirect:/home";
        }

        model.addAttribute("error", "Fel användarnamn eller lösenord");
        return "login";
    }
}
