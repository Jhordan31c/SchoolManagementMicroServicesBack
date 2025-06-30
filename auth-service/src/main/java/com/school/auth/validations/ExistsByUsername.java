package com.school.auth.validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = ExistsByUsernameValidation.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExistsByUsername {
    //Estos tres metodos los puedes copiar tambien desde la anotacion @NotBlank en el Entity
    String message() default "{ya existe en la base de datos}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
