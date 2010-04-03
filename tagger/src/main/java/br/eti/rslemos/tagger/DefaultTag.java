package br.eti.rslemos.tagger;

import java.util.HashMap;
import java.util.Map;


public class DefaultTag implements Tag, Cloneable {
	private static final Map<String, DefaultTag> cache = new HashMap<String, DefaultTag>();
	
	private final String value;

	public DefaultTag(String value) {
		this.value = value.intern();
	}

	public String getValue() {
		return value;
	}

	public DefaultTag intern() {
		if (cache.containsKey(value))
			return cache.get(value);
		else {
			cache.put(value, this);
			return this;
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		
		if (o == null)
			return false;
		
		if (!this.getClass().equals(o.getClass()))
			return false;
		
		DefaultTag other = (DefaultTag) o;
		
		return value != null ? value.equals(other.value) : other.value == null;
	}

	@Override
	public int hashCode() {
		return value != null ? value.hashCode() : 0;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("[").append(value).append("]");
		
		return result.toString();
	}
	
	@Override
	protected Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
}
