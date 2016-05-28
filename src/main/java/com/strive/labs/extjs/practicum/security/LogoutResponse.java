package com.strive.labs.extjs.practicum.security;

import com.strive.labs.extjs.practicum.json.JsonResponse;

public class LogoutResponse extends JsonResponse {
	public LogoutResponse(boolean success) {
		super(success);
		
		// Message
		if(success) {
			setMessage("You have been logged out successfully");
		} else {
			setMessage("Failed to log you out!");
		}
	}

	public LogoutResponse(boolean success, String message) {
		super(success, message);
	}
}
