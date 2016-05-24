package com.strive.labs.extjs.practicum.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicUpdate;

import com.strive.labs.extjs.practicum.Db;

@Entity
@Table(name=Db.TABLE_USERS)
@DynamicUpdate(value=true)
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Version @Column(name=Db.Version)
	private Long version;

	@Id @Column(name=Db.Id, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name=Db.UserName, nullable=false, columnDefinition="varchar(100)")
	private String name;

	@Column(name=Db.UserUsername, nullable=false, columnDefinition="varchar(20)")
	private String username;

	@Column(name=Db.UserPassword, nullable=false, columnDefinition="varchar(100)")
	private String password;

	@Column(name=Db.UserEmail, nullable=false, columnDefinition="varchar(100)")
	private String email;

	@Column(name=Db.UserPicture, nullable=true, columnDefinition="varchar(100)")
	private String picture;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	@JoinColumn(name=Db.UserGroup, nullable=false)
	private UserGroup user_group;
	
	public User() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public UserGroup getGroup() {
		return user_group;
	}

	public void setGroup(UserGroup group) {
		this.user_group = group;
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", username=" + username + ", email=" + email + ", group=" + user_group + "]";
	}
}
