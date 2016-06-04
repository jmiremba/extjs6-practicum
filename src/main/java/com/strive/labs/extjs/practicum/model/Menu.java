package com.strive.labs.extjs.practicum.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import com.strive.labs.extjs.practicum.Db;

@Entity
@Table(name=Db.TABLE_MENU)
@DynamicUpdate(value=true)
public class Menu implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Version @Column(name=Db.Version)
	private Long version;

	@Id @Column(name=Db.Id, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;			// Tree node ID

	@Column(name=Db.MenuText, nullable=false, columnDefinition="varchar(50)")
	private String node_text;	// Text displayed on tree node

	@Column(name=Db.MenuIconCls, nullable=true, columnDefinition="varchar(50)")
	private String icon_cls;	// Icon class

	@Column(name=Db.MenuViewXtype, nullable=true, columnDefinition="varchar(75)")
	private String view_xtype;	// Class type of content to be opened in view

	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	@JoinColumn(name=Db.MenuParent, nullable=true)
	private Menu menu_parent;	// ID of the parent node
	
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(name=Db.TABLE_MENU_GROUPS, joinColumns = { 
		@JoinColumn(name=Db.MenuGroup_MenuId, nullable=false, updatable=false)
	}, inverseJoinColumns = {
		@JoinColumn(name=Db.MenuGroup_GroupId, nullable=false, updatable=false) 
	})
	private Set<UserGroup> menu_groups;
	
	public Menu() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return node_text;
	}

	public void setText(String node_text) {
		this.node_text = node_text;
	}

	public String getIconClass() {
		return icon_cls;
	}

	public void setIconClass(String icon_cls) {
		this.icon_cls = icon_cls;
	}

	public String getViewXType() {
		return view_xtype;
	}

	public void setViewXType(String view_xtype) {
		this.view_xtype = view_xtype;
	}

	public Menu getParent() {
		return menu_parent;
	}

	public void setParent(Menu menu_parent) {
		this.menu_parent = menu_parent;
	}

	public Set<UserGroup> getGroups() {
		return menu_groups;
	}

	public void setGroups(Set<UserGroup> menu_groups) {
		this.menu_groups = menu_groups;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Menu other = (Menu) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ", node_text=" + node_text + ", icon_cls=" + icon_cls + ", view_xtype=" + view_xtype
				+ ", menu_parent=" + menu_parent + "]";
	}

	public boolean isGroup() {
		return StringUtils.isEmpty(this.view_xtype) && this.menu_parent == null;
	}
}
