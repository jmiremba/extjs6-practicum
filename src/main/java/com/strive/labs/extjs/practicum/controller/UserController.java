package com.strive.labs.extjs.practicum.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.strive.labs.extjs.practicum.Urls;
import com.strive.labs.extjs.practicum.Web;
import com.strive.labs.extjs.practicum.json.JsonResponse;
import com.strive.labs.extjs.practicum.security.LoginResponse;
import com.strive.labs.extjs.practicum.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value=Urls.Login, method=RequestMethod.POST, produces=Web.ContentTypeJson)
	@ResponseBody
	public JsonResponse createUser(HttpServletRequest request, 
			@RequestParam(required=true, value="username") String username, 
			@RequestParam(required=true, value="password") String password) {
		LoginResponse json = null;
		try {
			// Authenticate
			Long userId = userService.authenticate(username, password);
			json = new LoginResponse(userId != null);
		} catch(Exception ex) {
			json = new LoginResponse(false);
		}
		// Return
		return json;
	}
}
