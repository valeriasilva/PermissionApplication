package common.model;

import java.io.Serializable;

import common.util.CacheElement;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class File implements Serializable, CacheElement{

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private Long size;

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public LongProperty sizeProperty() {
		return new SimpleLongProperty(this.size);
	}

	public Long getId() {
		return id;
	}

	public LongProperty idProperty() {
		return new SimpleLongProperty(this.id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public SimpleStringProperty nameProperty() {
		return new SimpleStringProperty(this.name);
	}

	@Override
	public String getKey() {
		return getName();
	}

	public void setId(long id) {
		this.id = id;
	}
}
