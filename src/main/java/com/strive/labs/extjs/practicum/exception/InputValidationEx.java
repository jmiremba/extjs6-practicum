package com.strive.labs.extjs.practicum.exception;

import org.apache.log4j.Logger;

public class InputValidationEx  extends Exception {
	private static final long serialVersionUID = 1L;
	protected static final Logger logger = Logger.getLogger(InputValidationEx.class);
	
	public InputValidationEx(String validationMessage) {
		super(validationMessage);
		logger.error(this.getLocalizedMessage());
	}
}
