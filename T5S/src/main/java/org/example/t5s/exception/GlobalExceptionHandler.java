package org.example.t5s.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @Autowired
    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(BusinessException.class)
    public String handleBusinessException(BusinessException ex, HttpServletRequest request, RedirectAttributes redirectAttributes, Locale locale) {
        String translatedMessage = messageSource.getMessage(
                ex.getMessage(),
                null,
                ex.getMessage(),
                locale
        );

        redirectAttributes.addFlashAttribute("errorMessage", translatedMessage);

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null && !referer.isEmpty() ? referer : "/userlist.jhtml");
    }

    @ExceptionHandler(Exception.class)
    public String handleAllExceptions(Exception ex,
                                      HttpServletRequest request,
                                      RedirectAttributes redirectAttributes) {
        ex.printStackTrace();

        redirectAttributes.addFlashAttribute("errorMessage", "System error: " + ex.getMessage());

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/userlist.jhtml");
    }
}