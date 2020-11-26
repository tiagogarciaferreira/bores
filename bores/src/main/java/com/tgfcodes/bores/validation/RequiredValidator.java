package com.tgfcodes.bores.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.amazonaws.util.StringUtils;
import com.tgfcodes.bores.validation.annotation.Required;

public class RequiredValidator implements ConstraintValidator<Required, Object>  {

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		return !StringUtils.isNullOrEmpty(value.toString());
	}
}
