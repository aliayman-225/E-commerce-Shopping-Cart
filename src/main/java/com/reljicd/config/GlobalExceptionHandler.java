package com.reljicd.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles global exceptions across the application.
 * Logs errors and returns a custom error view.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles any exception thrown in the application.
     * 
     * @param exception the thrown exception
     * @param model the model to be used in the view
     * @return a ModelAndView object for error display
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleException(final Throwable exception, final Model model) {
        LOGGER.error("An error occurred during application execution", exception);

        ModelAndView mav = new ModelAndView("error");
        String errorDetails = (exception != null) ? exception.toString() : "Unknown error occurred";
        mav.addObject("errorMessage", errorDetails);
        return mav;
    }

}




