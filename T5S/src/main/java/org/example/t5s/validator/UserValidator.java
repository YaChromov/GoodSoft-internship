package org.example.t5s.validator;

import org.example.t5s.dto.Request.UserRequest;
import org.example.t5s.model.ValidationResult;
import org.springframework.stereotype.Component;


import java.time.LocalDate;

@Component
public class UserValidator {

    public ValidationResult validate(UserRequest request) {
        ValidationResult result = new ValidationResult();

        if (isInvalidString(request.getLogin())) {
            result.addError("login", "Логин обязателен");
        }

        String email = request.getEmail();
        if (isInvalidString(email) || !email.contains("@")) {
            result.addError("email", "Введите корректный email");
        }

        LocalDate birthday = request.getBirthday();
        if (birthday != null && birthday.isAfter(LocalDate.now())) {
            result.addError("birthday", "Дата не может быть в будущем");
        }

        if (request.getRoles() == null || request.getRoles().isEmpty()) {
            result.addError("role", "Роль обязательна");
        }

        return result;
    }

    private boolean isInvalidString(String s) {
        return s == null || s.trim().isEmpty();
    }
}