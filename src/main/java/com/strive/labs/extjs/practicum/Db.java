package com.strive.labs.extjs.practicum;

public final class Db {
	public static final String Id = "id";
	public static final String Version = "version";
	
	public static final String TABLE_GROUPS = "groups";
	public static final String GroupName = "name";
	
	public static final String TABLE_USERS = "users";
	public static final String UserName = "name";
	public static final String UserUsername = "username";
	public static final String UserPassword = "password";
	public static final String UserEmail = "email";
	public static final String UserPicture = "picture";
	public static final String UserGroup = "user_group";
	
	public static final String TABLE_MENU = "menus";
	public static final String MenuText = "node_text";
	public static final String MenuIconCls = "icon_cls";
	public static final String MenuViewXtype = "view_xtype";
	public static final String MenuParent = "menu_parent";
	public static final String MenuGroup = "menu_groups";
	
	public static final String TABLE_MENU_GROUPS = "menu_groups";
	public static final String MenuGroup_MenuId = "menu_id";
	public static final String MenuGroup_GroupId = "group_id";
}
