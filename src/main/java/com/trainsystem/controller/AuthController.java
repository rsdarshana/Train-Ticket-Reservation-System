package com.trainsystem.controller;

import com.trainsystem.model.User;
import com.trainsystem.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    
    private final AuthService authService;
    
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login";
    }
    
    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/signup";
    }
    
    @PostMapping("/signup")
    public String processSignup(@Valid @ModelAttribute("user") User user, 
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "auth/signup";
        }
        
        try {
            authService.registerUser(user);
            return "redirect:/login?signupSuccess";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/signup";
        }
    }
}