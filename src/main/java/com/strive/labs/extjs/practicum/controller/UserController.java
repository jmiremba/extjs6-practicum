package com.strive.labs.extjs.practicum.controller;

import java.io.File;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.strive.labs.extjs.practicum.AppData;
import com.strive.labs.extjs.practicum.Urls;
import com.strive.labs.extjs.practicum.Utils;
import com.strive.labs.extjs.practicum.Web;
import com.strive.labs.extjs.practicum.dto.user.SaveUserInput;
import com.strive.labs.extjs.practicum.json.JsonResponse;
import com.strive.labs.extjs.practicum.menu.MenuGroup;
import com.strive.labs.extjs.practicum.security.ListGroup;
import com.strive.labs.extjs.practicum.security.ListUser;
import com.strive.labs.extjs.practicum.security.LoginResponse;
import com.strive.labs.extjs.practicum.security.LogoutResponse;
import com.strive.labs.extjs.practicum.service.UserService;

@Controller
public class UserController {
	private static Logger LOGGER = Logger.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private AppData appData;
	
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
	
	@RequestMapping(value=Urls.UserList, method=RequestMethod.GET, produces=Web.ContentTypeJson)
	@ResponseBody
	public JsonResponse getUserList(HttpServletRequest request, 
			@RequestParam(value="id", required=false) Long userId) {
		JsonResponse json = new JsonResponse();
		try {
			// Users
			List<ListUser> menus = userService.getUserList(userId);
			
			// Response
			json.setSuccessData(menus);
		} catch(Exception ex) {
			json.setException(ex);
		}
		return json;
	}
	
	@RequestMapping(value=Urls.GroupList, method=RequestMethod.GET, produces=Web.ContentTypeJson)
	@ResponseBody
	public JsonResponse getGroupList(HttpServletRequest request) {
		JsonResponse json = new JsonResponse();
		try {
			// Groups
			List<ListGroup> menus = userService.getGroupList();
			
			// Response
			json.setSuccessData(menus);
		} catch(Exception ex) {
			json.setException(ex);
		}
		return json;
	}
	
	@RequestMapping(value=Urls.UserSave, method=RequestMethod.POST, produces=Web.ContentTypeJson)
	@ResponseBody
	public JsonResponse saveUser(HttpServletRequest request,
			@ModelAttribute("savedUser") SaveUserInput input,
			@RequestParam(value="picture", required=false) MultipartFile picture) {
		JsonResponse json = null;
		try {
			// Picture
			File profileImage = null;
			if(picture != null) {
				profileImage = new File(appData.getProfileImageDirectory(), picture.getOriginalFilename());
				picture.transferTo(profileImage);
				LOGGER.debug("Profile picture at "+profileImage.getAbsolutePath());
			}
			
			// Save
			userService.saveUser(input, profileImage);
			json = new JsonResponse(true, "Successfully saved user");
		} catch(Exception ex) {
			json = new JsonResponse(false, ex.getMessage());
			LOGGER.error(ex);
		}
		// Return
		return json;
	}
	
	@RequestMapping(value=Urls.UserDelete, method=RequestMethod.POST, produces=Web.ContentTypeJson)
	@ResponseBody
	public JsonResponse deleteUser(HttpServletRequest request, 
			@RequestParam(value="data", required=true) String jsonData) {
		JsonResponse json = null;
		try {
			// Users
			@SuppressWarnings("unchecked")
			List<ListUser> users = Utils.fromJson(jsonData, List.class, ListUser.class);
			
			// Delete
			for(ListUser user : users) {
				userService.deleteUser(user.getId());
	
				// Picture
				if(StringUtils.isNotEmpty(user.getPicture())) {
					File profileImage = new File(appData.getProfileImageDirectory(), user.getPicture());
					if(profileImage.exists() && profileImage.delete()) {
						LOGGER.debug("Deleted profile pictire "+profileImage.getAbsolutePath());
					}
				}
				json = new JsonResponse(true, "Successfully deleted "+user.getFullnames());
			}
		} catch(Exception ex) {
			json = new JsonResponse(false, ex.getMessage());
			LOGGER.error(ex);
		}
		// Return
		return json;
	}
}