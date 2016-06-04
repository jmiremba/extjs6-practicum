package com.strive.labs.extjs.practicum.service;

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
import com.strive.labs.extjs.practicum.menu.MenuGroup;
import com.strive.labs.extjs.practicum.menu.MenuItem;
import com.strive.labs.extjs.practicum.model.Menu;
import com.strive.labs.extjs.practicum.model.User;

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
}
