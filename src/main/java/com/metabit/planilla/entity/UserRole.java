package com.metabit.planilla.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="user_roles", uniqueConstraints = @UniqueConstraint(
		columnNames = {"role", "user_id"}))
public class UserRole {
	
	@Id
	@GeneratedValue
	@Column(name="user_role_id", unique=true, nullable = false)
	private Integer userRoleId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id", nullable = false)
	private User user;
	
	@Column(name="role", nullable=false, length=45)
	private String role;
	public UserRole(User user, String role) {
		super();
		this.user = user;
		this.role = role;
	}
	
	public UserRole() {
	}

	public Integer getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
}
