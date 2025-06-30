package com.school.auth.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.school.auth.services.UserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class ExistsByUsernameValidation implements ConstraintValidator<ExistsByUsername, String> {
    
    @Autowired
    private UserService us;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (us == null) {
            return true;
        }
        return !us.existsByUsername(username);
    }
    
}
