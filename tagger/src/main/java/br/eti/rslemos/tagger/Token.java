package br.eti.rslemos.tagger;

import java.util.Map;

public interface Token {
	public static final String WORD = "_text";
	public static final String POS = "_pos";

	Object getFeature(String name);
	Token setFeature(String name, Object value);
	
	Map<String, Object> getFeatures();
}
