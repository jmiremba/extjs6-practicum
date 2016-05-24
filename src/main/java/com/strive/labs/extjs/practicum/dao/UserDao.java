package com.strive.labs.extjs.practicum.dao;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.strive.labs.extjs.practicum.Db;
import com.strive.labs.extjs.practicum.Spring;
import com.strive.labs.extjs.practicum.model.User;

@Repository(Spring.UserDao)
public class UserDao {
	// Logger
	private static Logger logger = Logger.getLogger(UserDao.class);
	
	@Autowired(required=true)
	private SessionFactory sessionFactory;
	
	public User authenticate(String username, String password) {
		// Session
		Session session = this.sessionFactory.getCurrentSession();
		
		// Query
		String hql = "select u from "+User.class.getName()+" u where u."+Db.UserUsername+
				" = :username and u."+Db.UserPassword+" = :password";
		Query query = session.createQuery(hql)
				.setString("username", username)
				.setString("password", password);
		User user = (User) query.uniqueResult();
		
		// Return
		logger.debug("Authenticated: "+user);
		return user;
	}
}
