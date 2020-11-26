package com.tgfcodes.bores.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.tgfcodes.bores.validation.annotation.Mail;

public class MailValidator implements ConstraintValidator<Mail, Object>  {
	
	private final String PADRAO = "^[0-9?A-z0-9?]+(\\.)?[0-9?A-z0-9?]+@[A-z]+\\.[A-z]{3}.?[A-z]{0,3}$";

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		return ((String)value).matches(this.PADRAO);
	}
}
