package com.sda.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 */
@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleInternalServerException(Model model, Exception e) {
        model.addAttribute("errorMsg", e.getMessage());

        return new ModelAndView("error/error-500", model.asMap());
    }
}
