package common.model;

import java.io.Serializable;

import common.util.CacheElement;

public class File implements Serializable, CacheElement{

	private Long id;

	private String name;

	private Long size;

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

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	@Override
	public String getKey() {
		return getName();
	}
}
