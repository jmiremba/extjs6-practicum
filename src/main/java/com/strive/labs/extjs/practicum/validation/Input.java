package com.strive.labs.extjs.practicum.validation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.StringUtils;

import com.strive.labs.extjs.practicum.dto.user.SaveUserInput;
import com.strive.labs.extjs.practicum.exception.InputValidationEx;

public class Input {
	private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
	private static Validator inputValidator = validatorFactory.getValidator();
	
	// Email validation regex
	private static final String ec = "[a-zA-Z0-9\\.\\+_-]*";
	public static final String EMAIL_REGEX = "^\\w+"+ec+"@\\w+"+ec+"[a-zA-Z]{2,4}$";
	
	@SuppressWarnings("rawtypes")
	private static String getValidationMessages(Set violations, String... messagesToIgnore) {
		// Messages to ignore
		Set<String> _messagesToIgnore = new HashSet<>();
		if(messagesToIgnore != null && messagesToIgnore.length > 0) {
			_messagesToIgnore.addAll(Arrays.asList(messagesToIgnore));
		}
		// Violations
		if(violations.size() > 0) {
			Set<String> messages = new HashSet<>();
			for(Object violation: violations) {
				ConstraintViolation v = (ConstraintViolation) violation;
				if(!_messagesToIgnore.contains(v.getMessage())) {
					messages.add(v.getMessage()+" ["+v.getInvalidValue()+"]");
				}
			}
			return StringUtils.join(messages, "\n");
		}
		return null;
	}
	
	public static void validate(SaveUserInput input) throws InputValidationEx {
		// Violations
		Set<ConstraintViolation<SaveUserInput>> violations = inputValidator.validate(input);
		String violationMessage = getValidationMessages(violations);
		if(StringUtils.isNotEmpty(violationMessage)) {
			throw new InputValidationEx(violationMessage);
		}
		
		// Tidying up
		input.setEmail(StringUtils.lowerCase(input.getEmail()));
		input.setFullnames(StringUtils.capitalize(input.getFullnames()));
		input.setUsername(StringUtils.lowerCase(input.getUsername()));
	}
}
