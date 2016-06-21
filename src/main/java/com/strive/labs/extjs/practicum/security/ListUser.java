package com.strive.labs.extjs.practicum.security;

import com.strive.labs.extjs.practicum.model.User;

public class ListUser {
	private Long id;
	private String fullnames;
	private String username;
	private String email;
	private String picture;
	private Long groupId;
	
	public ListUser() {}
	
	public ListUser(User user) {
		this.id = user.getId();
		this.fullnames = user.getName();
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.picture = user.getPicture();
		this.groupId = user.getGroup().getId();
	}

	public Long getId() {
		return id;
	}

	public String getFullnames() {
		return fullnames;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPicture() {
		return picture;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFullnames(String fullnames) {
		this.fullnames = fullnames;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
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
		ListUser other = (ListUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ListUser [id=" + id + ", names=" + fullnames + ", username=" + username + ", email=" + email + ", groupId="
				+ groupId + ", picture=" + picture + "]";
	}
}
