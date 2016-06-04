package com.strive.labs.extjs.practicum.menu;

import java.util.ArrayList;
import java.util.List;

import com.strive.labs.extjs.practicum.model.Menu;

public class MenuGroup {
	private Long id;
	private String text;
	private String iconCls;
	private List<MenuItem> items;
	
	public MenuGroup(Menu m) {
		this.id = m.getId();
		this.text = m.getText();
		this.iconCls = m.getIconClass();
		this.items = new ArrayList<>();
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

	public List<MenuItem> getItems() {
		return items;
	}
}
