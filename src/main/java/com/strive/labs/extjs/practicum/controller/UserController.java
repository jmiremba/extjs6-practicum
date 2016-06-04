package com.strive.labs.extjs.practicum.controller;

import java.util.List;

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
import com.strive.labs.extjs.practicum.menu.MenuGroup;
import com.strive.labs.extjs.practicum.security.LoginResponse;
import com.strive.labs.extjs.practicum.security.LogoutResponse;
import com.strive.labs.extjs.practicum.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value=Urls.Login, method=RequestMethod.POST, produces=Web.ContentTypeJson)
	@ResponseBody
	public JsonResponse doLogin(HttpServletRequest request, 
			@RequestParam(required=true, value="username") String username, 
			@RequestParam(required=true, value="password") String password) {
		LoginResponse json = null;
		try {
			// Authenticate
			Long userId = userService.authenticate(username, password);
			json = new LoginResponse(userId != null);
		} catch(Exception ex) {
			json = new LoginResponse(false, ex.getMessage());
		}
		// Return
		return json;
	}
	
	@RequestMapping(value=Urls.Logout, method=RequestMethod.GET, produces=Web.ContentTypeJson)
	@ResponseBody
	public JsonResponse doLogout(HttpServletRequest request) {
		LogoutResponse json = null;
		try {
			// End session
			request.getSession().invalidate();
			json = new LogoutResponse(true);
		} catch(Exception ex) {
			json = new LogoutResponse(false, ex.getMessage());
		}
		// Return
		return json;
	}
	
	@RequestMapping(value=Urls.Session, method=RequestMethod.GET, produces=Web.ContentTypeJson)
	@ResponseBody
	public JsonResponse checkSession(HttpServletRequest request) {
		JsonResponse json = new JsonResponse(true);
		// Return
		return json;
	}
	
	@RequestMapping(value=Urls.Menu, method=RequestMethod.GET, produces=Web.ContentTypeJson)
	@ResponseBody
	public JsonResponse getMenu(HttpServletRequest request) {
		JsonResponse json = new JsonResponse();
		try {
			// Logged in user
			String username = "prideafrica";
			
			// Menus
			List<MenuGroup> menus = userService.getMenu(username);
			
			// Response
			json.setSuccessData(menus);
		} catch(Exception ex) {
			json = new JsonResponse(false, ex.getMessage());
		}
		return json;
	}
}