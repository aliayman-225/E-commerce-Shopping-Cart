package com.reljicd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class AuthenticationController {

    @GetMapping("/login")
    public String showLoginPage(Principal userPrincipal) {
        // If the user is already authenticated, redirect them to the home page
        if (userPrincipal != null) {
            return "redirect:/home";
        }
        // Otherwise, show the login page
        return "/login";
    }
}
