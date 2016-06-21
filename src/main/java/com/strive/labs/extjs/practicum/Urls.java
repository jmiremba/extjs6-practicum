package com.strive.labs.extjs.practicum;

public final class Urls {
	public static final String Login = "login.json";
	public static final String Logout = "logout.json";
	public static final String Session = "session.json";
	public static final String Menu = "menu.json";
	
	// Security 
	private static final String _SECURITY = "security/";
	
	// Users
	private static final String _USER = _SECURITY+"user/";
	public static final String UserList = _USER+"list.json";
	public static final String UserSave = _USER+"save.json";
	public static final String UserDelete = _USER+"delete.json";
	
	// Groups
	private static final String _GROUP = _SECURITY+"group/";
	public static final String GroupList = _GROUP+"list.json";
}
