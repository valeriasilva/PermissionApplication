package common.model;

import java.io.Serializable;
import java.sql.Timestamp;

import client.util.Util;

public class Feature implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String description;
	private Timestamp creationDate;
	private Plugin plugin;

	public Feature() {
		this.setName("");
		this.setDescription("");
		this.plugin = new Plugin();
		this.setCreationDate(Util.getCurrentDateFormated());
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(final Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Plugin getPlugin() {
		return plugin;
	}

	public void setPlugin(final Plugin plugin) {
		this.plugin = plugin;
	}

}
