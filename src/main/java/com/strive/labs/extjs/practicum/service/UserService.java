package com.strive.labs.extjs.practicum.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strive.labs.extjs.practicum.Spring;
import com.strive.labs.extjs.practicum.dao.UserDao;
import com.strive.labs.extjs.practicum.dto.user.SaveUserInput;
import com.strive.labs.extjs.practicum.exception.InputValidationEx;
import com.strive.labs.extjs.practicum.menu.MenuGroup;
import com.strive.labs.extjs.practicum.menu.MenuItem;
import com.strive.labs.extjs.practicum.model.Menu;
import com.strive.labs.extjs.practicum.model.User;
import com.strive.labs.extjs.practicum.model.UserGroup;
import com.strive.labs.extjs.practicum.security.ListGroup;
import com.strive.labs.extjs.practicum.security.ListUser;
import com.strive.labs.extjs.practicum.validation.Input;

@Service(Spring.UserService)
@Transactional(readOnly=true, rollbackFor=HibernateException.class)
public class UserService {
	private static Logger logger = Logger.getLogger(UserService.class);
	
	@Autowired
    private UserDao userDao;

	public Long authenticate(String username, String password) throws Exception {
		logger.debug("Authenticating {username="+username+", password="+password+"}");
		
		// Authenticate
		User user = userDao.authenticate(username, password);
		if(user == null) {
			throw new Exception("No user found matching username and password");
		}
		
		// Return
		return user.getId();
	}

	public List<MenuGroup> getMenu(String username) {
		// Menus for user
		List<Menu> userMenus = userDao.getMenus(username);
		
		// Menu groups
		Map<Long, MenuGroup> menuGroupMap = new HashMap<>();
		for(Menu m : userMenus) {
			if(m.isGroup()) {
				MenuGroup group = new MenuGroup(m);
				menuGroupMap.put(group.getId(), group);
			} else {
				Menu parent = m.getParent();
				MenuGroup group = menuGroupMap.get(parent.getId());
				
				// Item
				MenuItem item = new MenuItem(m);
				group.getItems().add(item);
			}
		}
		
		// Return
		List<MenuGroup> menus = new ArrayList<>();
		menus.addAll(menuGroupMap.values());
		logger.debug("Obtained "+menus.size()+" menu groups for {username="+username+"}");
		return menus;
	}

	public List<ListUser> getUserList(Long userId) {
		// Users
		List<User> users = userDao.getUsers(userId);
		
		// Listed
		List<ListUser> listed = new ArrayList<>();
		users.forEach(u -> {
			listed.add(new ListUser(u));
		});
		
		// Return
		logger.debug("Obtained "+listed.size()+" list users");
		return listed;
	}

	public List<ListGroup> getGroupList() {
		// Groups
		List<UserGroup> groups = userDao.getGroups();
		
		// Listed
		List<ListGroup> listed = new ArrayList<>();
		groups.forEach(g -> {
			listed.add(new ListGroup(g));
		});
		
		// Return
		logger.debug("Obtained "+listed.size()+" list groups");
		return listed;
	}

	@Transactional(readOnly=false)
	public void saveUser(SaveUserInput input, File profileImage) throws InputValidationEx {
		// Validation
		Input.validate(input);
		
		// User
		Long userId = input.getId();
		User user = null;
		if(userId == null) {
			user = new User();
			user.setPassword(input.getUsername()+"@P4$");
		} else {
			user = userDao.getUser(userId);
		}
		
		// Update
		user.setEmail(input.getEmail());
		user.setName(input.getFullnames());
		user.setUsername(input.getUsername());
		if(profileImage != null) {
			user.setPicture(profileImage.getName());
		}
		
		// Group
		Long groupId = input.getGroupId();
		UserGroup group = userDao.getGroup(groupId);
		user.setGroup(group);
		
		// Save
		userDao.saveUser(user);
		input.setId(user.getId());
	}

	@Transactional(readOnly=false)
	public void deleteUser(Long userId) {
		userDao.deleteUser(userId);
	}
}
