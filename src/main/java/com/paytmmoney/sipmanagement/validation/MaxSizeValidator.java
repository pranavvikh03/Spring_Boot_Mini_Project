package com.paytmmoney.sipmanagement.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = MaxSizeConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxSizeValidator {
    String message() default "Total Bank Accounts should not be more than 5";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
