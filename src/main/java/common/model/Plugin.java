package common.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

import client.util.Util;

public class Plugin implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Plugin() {
		this.setName("");
		this.setDescription("");
		this.setFeatures(new ArrayList());
		this.setCreationDate(Util.getCurrentDateFormated());
	}

	private Long id;
	private String name;
	private String description;
	private Timestamp creationDate;
	private ArrayList<Feature> features;

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

	public ArrayList<Feature> getFeatures() {
		return features;
	}

	public void setFeatures(final ArrayList<Feature> features) {
		this.features = features;
	}

}
