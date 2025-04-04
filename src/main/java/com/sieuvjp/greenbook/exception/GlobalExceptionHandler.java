package com.sieuvjp.greenbook.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFoundException(
            ResourceNotFoundException ex,
            Model model,
            HttpServletRequest request) {

        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorCode", HttpStatus.NOT_FOUND.value());
        model.addAttribute("timestamp", System.currentTimeMillis());
        model.addAttribute("path", request.getRequestURI());

        return "error/404";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGenericException(
            Exception ex,
            Model model,
            HttpServletRequest request) {

        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute("timestamp", System.currentTimeMillis());
        model.addAttribute("path", request.getRequestURI());

        return "error/500.html";
    }
}