package br.eti.rslemos.tagger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class DefaultToken extends AbstractToken {

	private final Map<String, Object> features = new HashMap<String, Object>();

	public DefaultToken(String word) {
		features.put(AbstractToken.WORD, word);
	}

	public DefaultToken(Token token) {
		features.putAll(token.getFeatures());
	}

	public Object getFeature(String name) {
		return features.get(name);
	}

	public DefaultToken setFeature(String name, Object value) {
		features.put(name, value);
		return this;
	}

	public Map<String, Object> getFeatures() {
		return Collections.unmodifiableMap(features);
	}

	@Override
	public String toString() {
		return getFeature(AbstractToken.WORD) + "/" + getFeature(AbstractToken.POS);
	}

	
}
