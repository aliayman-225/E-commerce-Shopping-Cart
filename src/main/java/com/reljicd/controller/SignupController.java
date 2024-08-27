package com.reljicd.controller;

import com.reljicd.model.User;
import com.reljicd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class SignupController {

    // Service class for user-related operations, such as saving a user or checking for existing users
    private final UserService userService;

    // Constructor-based dependency injection to inject the UserService into this controller
    @Autowired
    public SignupController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Displays the registration form to the user.
     * This method is mapped to the GET request for the /signup URL.
     *
     * @return ModelAndView object containing the view name and the new User object
     */
    @GetMapping("/registration")
    public ModelAndView showRegistrationForm() {
        // Create a new ModelAndView object for returning the view and model data
        ModelAndView mav = new ModelAndView();

        // Create a new, empty User object to bind to the form inputs in the view
        mav.addObject("user", new User());

        // Set the view name to the signup page (registration form)
        mav.setViewName("/registration");

        // Return the ModelAndView object to display the form
        return mav;
    }

    /**
     * Handles the submission of the registration form.
     * This method is mapped to the POST request for the /signup URL.
     * 
     * @param user The User object populated with form data, validated using annotations
     * @param result BindingResult that holds the result of validation and binding
     * @return ModelAndView object containing the view name and any necessary model data
     */
    @PostMapping("/registration")
    public ModelAndView registerUser(@Valid User user, BindingResult result) {
        // Create a new ModelAndView object for returning the view and model data
        ModelAndView mav = new ModelAndView();

        // Check if an account already exists with the provided email address
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            // Reject the email field and add an error message to the BindingResult
            result.rejectValue("email", "error.user",
                    "A user is already registered with the provided email");
        }

        // Check if an account already exists with the provided username
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            // Reject the username field and add an error message to the BindingResult
            result.rejectValue("username", "error.user",
                    "A user is already registered with the provided username");
        }

        // Check if there are any validation errors in the BindingResult
        if (result.hasErrors()) {
            // If there are errors, reload the signup page so the user can correct the inputs
            mav.setViewName("/registration");
        } else {
            // If the input is valid and no existing user was found with the same email or username:
            
            // Save the new user, typically setting their role to USER and marking them as active
            userService.saveUser(user);

            // Add a success message to inform the user of successful registration
            mav.addObject("successMessage", "Registration successful");

            // Add a new, empty User object to clear the form after successful registration
            mav.addObject("user", new User());

            // Set the view name back to the signup page to allow the user to register another account
            mav.setViewName("/registration");
        }

        // Return the ModelAndView object, either reloading the form with errors or with success
        return mav;
    }
}
