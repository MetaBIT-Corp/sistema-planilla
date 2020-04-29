package com.metabit.planilla.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue
	@Column(name = "id_user", unique = true, nullable = false)
	private int idUser;

	@Column(name = "username", unique = true, nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name="enabled", nullable = false)
	private boolean enabled;
	
	@Column(name="account_experired", nullable = false)
	private boolean accountExperired;
	
	@Column(name="account_locked", nullable = false)
	private boolean accountLocked;
	
	@Column(name="password_expired", nullable = false)
	private boolean passwordExpired;
	
	@Column(name="intentos", nullable = false)
	private int intentos;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	private Set<UserRole> userRole = new HashSet<UserRole>();

	public User(int idUser, String username, String password, boolean enabled, boolean accountExperired, boolean accountLocked,
			boolean passwordExpired, int intentos) {
		super();
		this.idUser = idUser;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.accountExperired = accountExperired;
		this.accountLocked = accountLocked;
		this.passwordExpired = passwordExpired;
		this.intentos = intentos;
	}

	public User(int idUser, String username, String password, boolean enabled, boolean accountExperired, boolean accountLocked,
			boolean passwordExpired, int intentos, Set<UserRole> userRole) {
		super();
		this.idUser = idUser;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.accountExperired = accountExperired;
		this.accountLocked = accountLocked;
		this.passwordExpired = passwordExpired;
		this.intentos = intentos;
		this.userRole = userRole;
	}

	public User() {
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
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

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean getAccountExperired() {
		return accountExperired;
	}

	public void setAccountExperired(boolean accountExperired) {
		this.accountExperired = accountExperired;
	}

	public boolean getAccountLocked() {
		return accountLocked;
	}

	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	public boolean getPasswordExpired() {
		return passwordExpired;
	}

	public void setPasswordExpired(boolean passwordExpired) {
		this.passwordExpired = passwordExpired;
	}

	public int getIntentos() {
		return intentos;
	}

	public void setIntentos(int intentos) {
		this.intentos = intentos;
	}

	public Set<UserRole> getUserRole() {
		return userRole;
	}

	public void setUserRole(Set<UserRole> userRole) {
		this.userRole = userRole;
	}
	
	
	
	
	
}
