package common.model;

import java.io.Serializable;

import common.util.CacheElement;

public class User implements Serializable, CacheElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String login;
	private String fullname;
	private boolean status;
	private String currentManagement;

	public User() {
		this.setLogin("");
		this.setFullname("");
		this.setCurrentManagement("");
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(final String login) {
		this.login = login;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(final String fullname) {
		this.fullname = fullname;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(final boolean status) {
		this.status = status;
	}

	public String getCurrentManagement() {
		return currentManagement;
	}

	public void setCurrentManagement(final String currentManagement) {
		this.currentManagement = currentManagement;
	}

	@Override
	public String getKey() {
		return getFullname();
	}

}
