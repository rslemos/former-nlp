package br.eti.rslemos.tagger;

import java.util.Map;

public interface Token {
	Object getFeature(String name);
	Token setFeature(String name, Object value);
	
	Map<String, Object> getFeatures();
}
