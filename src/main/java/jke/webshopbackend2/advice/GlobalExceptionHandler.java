package jke.webshopbackend2.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public String handleNullPointerException(NullPointerException e) {
        
        return "redirect:/login";
    }
}
