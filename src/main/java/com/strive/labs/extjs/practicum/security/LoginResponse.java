package com.strive.labs.extjs.practicum.security;

import com.strive.labs.extjs.practicum.json.JsonResponse;

public class LoginResponse extends JsonResponse {
	public LoginResponse(boolean success) {
		super(success);
		
		// Message
		if(success) {
			setMessage("Authentication was successful");
		} else {
			setMessage("Authentication failed: check username or password");
		}
	}

	public LoginResponse(boolean success, String message) {
		super(success, message);
	}
}
