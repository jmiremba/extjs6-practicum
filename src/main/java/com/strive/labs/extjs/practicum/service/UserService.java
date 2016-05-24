package com.strive.labs.extjs.practicum.service;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strive.labs.extjs.practicum.Spring;
import com.strive.labs.extjs.practicum.dao.UserDao;
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
}
