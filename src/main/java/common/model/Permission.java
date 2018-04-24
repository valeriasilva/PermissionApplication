package common.model;

import java.io.Serializable;

public class Permission implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long user;
	private Long feature;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(final Long user) {
		this.user = user;
	}

	public Long getFeature() {
		return feature;
	}

	public void setFeature(final Long feature) {
		this.feature = feature;
	}

}
