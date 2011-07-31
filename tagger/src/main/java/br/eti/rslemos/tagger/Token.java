package br.eti.rslemos.tagger;

import java.util.Map;

public interface Token {
	@Deprecated String getWord();
	
	@Deprecated Object getTag();
	@Deprecated Token setTag(Object tag);
	
	Object getFeature(String name);
	Token setFeature(String name, Object value);
	
	Map<String, Object> getFeatures();
}
