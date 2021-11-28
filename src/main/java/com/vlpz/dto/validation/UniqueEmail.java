package com.vlpz.dto.validation;

import com.vlpz.dto.validation.UniqueEmail.UniqueEmailValidator;
import com.vlpz.service.UserService;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import org.springframework.beans.factory.annotation.Autowired;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface UniqueEmail {

  String message() default "{Email is not unique}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
      return !userService.isUserExistsWithEmail(email);
    }
  }

}
