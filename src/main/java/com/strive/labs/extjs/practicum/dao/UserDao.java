package com.strive.labs.extjs.practicum.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.strive.labs.extjs.practicum.Db;
import com.strive.labs.extjs.practicum.Spring;
import com.strive.labs.extjs.practicum.model.Menu;
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

	@SuppressWarnings("unchecked")
	public List<Menu> getMenus(String username) {
		// Session
		Session session = this.sessionFactory.getCurrentSession();
		
		// User
		
		// Menus
		String hql = "select m from "+Menu.class.getName()+" m join m."+Db.MenuGroup+" g where g."+Db.Id+
				" = (select z."+Db.Id+" from "+User.class.getName()+" u join u."+Db.UserGroup+" z where u."+
				Db.UserUsername+" = :username) order by m."+Db.MenuParent+" asc";
		Query query = session.createQuery(hql)
				.setString("username", username);
		List<Menu> _menus = query.list();
		
		// Return
		logger.debug("Obtained: "+_menus.size()+" menus for {username="+username+"}");
		return _menus;
	}
}
