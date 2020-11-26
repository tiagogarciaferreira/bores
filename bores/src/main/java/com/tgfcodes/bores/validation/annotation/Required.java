package com.tgfcodes.bores.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.tgfcodes.bores.validation.RequiredValidator;

@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {RequiredValidator.class})
public @interface Required {

	String message() default "Campo obrigatório.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
}
