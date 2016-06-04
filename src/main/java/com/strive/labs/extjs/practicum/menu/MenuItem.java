package com.strive.labs.extjs.practicum.menu;

import com.strive.labs.extjs.practicum.model.Menu;

public class MenuItem {
	private Long id;
	private String text;
	private String iconCls;
	private String className;
	private Long parentId;
	private boolean leaf;
	
	public MenuItem(Menu m) {
		this.id = m.getId();
		this.text = m.getText();
		this.iconCls = m.getIconClass();
		this.className = m.getViewXType();
		this.parentId = m.getParent().getId();
		this.leaf = true;
	}

	public Long getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public String getClassName() {
		return className;
	}

	public Long getParentId() {
		return parentId;
	}

	public boolean isLeaf() {
		return leaf;
	}
}
