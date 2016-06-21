package com.strive.labs.extjs.practicum.dao;

import java.util.ArrayList;
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
import com.strive.labs.extjs.practicum.model.UserGroup;

@Repository(Spring.UserDao)
public class UserDao {
	// Logger
	private static final Logger LOGGER = Logger.getLogger(UserDao.class);
	
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
		LOGGER.debug("Authenticated: "+user);
		return user;
	}

	@SuppressWarnings("unchecked")
	public List<Menu> getMenus(String username) {
		// Session
		Session session = this.sessionFactory.getCurrentSession();
		
		// Menus
		String hql = "select m from "+Menu.class.getName()+" m join m."+Db.MenuGroup+" g where g."+Db.Id+
				" = (select z."+Db.Id+" from "+User.class.getName()+" u join u."+Db.UserGroup+" z where u."+
				Db.UserUsername+" = :username) order by m."+Db.MenuParent+" asc";
		Query query = session.createQuery(hql)
				.setString("username", username);
		List<Menu> _menus = query.list();
		
		// Return
		LOGGER.debug("Obtained: "+_menus.size()+" menus for {username="+username+"}");
		return _menus;
	}

	@SuppressWarnings("unchecked")
	public List<User> getUsers(Long userId) {
		// Session
		Session session = this.sessionFactory.getCurrentSession();
		
		// Menus
		String hql = "select u from "+User.class.getName()+" u join fetch u."+Db.UserGroup+" g";
		if(userId != null) { hql += " where u."+Db.Id+" = :userId"; }
		Query query = session.createQuery(hql);
		if(userId != null) { query.setLong("userId", userId); }
		List<User> users = query.list();
		
		// Return
		LOGGER.debug("Found "+users.size()+" users");
		return users;
	}

	@SuppressWarnings("unchecked")
	public List<UserGroup> getGroups() {
		// Session
		Session session = this.sessionFactory.getCurrentSession();
		
		// Menus
		String hql = "select g from "+UserGroup.class.getName()+" g";
		Query query = session.createQuery(hql);
		List<UserGroup> groups = query.list();
		
		// Return
		LOGGER.debug("Found "+groups.size()+" groups");
		return groups;
	}

	public User getUser(Long userId) {
		// Session
		Session session = this.sessionFactory.getCurrentSession();
		
		// Menus
		String hql = "select u from "+User.class.getName()+" u where u."+Db.Id+" = :userId";
		Query query = session.createQuery(hql).setLong("userId", userId);
		User user = (User) query.uniqueResult();
		
		// Return
		LOGGER.debug("Obtained "+user);
		return user;
	}

	public UserGroup getGroup(Long groupId) {
		// Session
		Session session = this.sessionFactory.getCurrentSession();
		
		// Menus
		String hql = "select g from "+UserGroup.class.getName()+" g where g."+Db.Id+" = :groupId";
		Query query = session.createQuery(hql).setLong("groupId", groupId);
		UserGroup group = (UserGroup) query.uniqueResult();
		
		// Return
		LOGGER.debug("Obtained "+group);
		return group;
	}

	public void saveUser(User user) {
		// Session
		Session session = this.sessionFactory.getCurrentSession();
		// Save
		session.save(user);
		LOGGER.debug("Saved "+user);
	}

	public void deleteUser(Long userId) {
		List<Long> userIds = new ArrayList<>();
		userIds.add(userId);
		deleteUsers(userIds);
	}

	@SuppressWarnings("unchecked")
	public void deleteUsers(List<Long> userIds) {
		// Session
		Session session = this.sessionFactory.getCurrentSession();
		
		// Users
		if(userIds.size() > 0) {
			String hql = "select u from "+User.class.getName()+" u where u."+Db.Id+" in (:userIds)";
			Query query = session.createQuery(hql).setParameterList("userIds", userIds);
			List<User> users = query.list();
			
			// Delete
			for(User user : users) {
				session.delete(user);
				LOGGER.debug("Deleted "+user);
			}
		}
		LOGGER.debug(userIds.size()+" users deleted");
	}
}
