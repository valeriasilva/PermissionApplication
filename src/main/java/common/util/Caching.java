package common.util;

import java.util.HashMap;
import java.util.Map;

public class Caching<T extends CacheElement> {

	private Map<String, T> elements;
	private int cacheMiss;
	private int cacheHit;

	public Caching() {
		elements = new HashMap<>();
		cacheHit = 0;
		cacheMiss = 0;
	}

	public void cacheElement(T element) {
		synchronized (elements) {
			elements.put(element.getKey().toUpperCase(), element);
		}
	}

	public void unCacheElement(String key) {
		synchronized (elements) {
			elements.remove(key.toUpperCase());
		}
	}

	public T getElement(String key) {
		T element = elements.get(key.toUpperCase());
		if (element != null) {
			++cacheHit;
		} else {
			++cacheMiss;
		}
		return element;
	}
}
