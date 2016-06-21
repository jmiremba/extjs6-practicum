package com.strive.labs.extjs.practicum.dto.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern.Flag;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.strive.labs.extjs.practicum.validation.Input;

public class SaveUserInput {
	private Long id;

	@NotEmpty(message="Fullname is required")
	@Length(min=3, max=50, message="Fullname must be between {min} and {max} characters long")
	private String fullnames;

	@NotEmpty(message="Email is required")
	@Length(min=5, max=75, message="Email must be between {min} and {max} characters long")
	@Email(regexp=Input.EMAIL_REGEX, flags={Flag.CASE_INSENSITIVE}, message="Invalid email address {value}")
	private String email;

	@NotEmpty(message="Username is required")
	@Length(min=3, max=25, message="Username must be between {min} and {max} characters long")
	private String username;
	
	@NotNull(message="Group is required")
	private Long groupId;
	
	public SaveUserInput() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullnames() {
		return fullnames;
	}

	public void setFullnames(String fullnames) {
		this.fullnames = fullnames;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	@Override
	public String toString() {
		return "SaveUserInput [id=" + id + ", fullnames=" + fullnames + ", email=" + email + ", username=" + username
				+ ", groupId=" + groupId + "]";
	}
}
