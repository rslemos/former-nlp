package br.eti.rslemos.brill;

import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;

import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.SentenceIndexOutOfBoundsException;
import br.eti.rslemos.tagger.Token;

public class SentenceContext implements Context {
	private static final class NullToken implements Token {
		public Object getFeature(String name) {
			return null;
		}

		public Token setFeature(String name, Object value) {
			throw new IllegalStateException("Can'Object set NULL token tag '" + name + "' to '" + value + "'");
		}

		public Map<String, Object> getFeatures() {
			return Collections.emptyMap();
		}
	}

	public static final Token NULL_TOKEN = new NullToken();
	
	private final Sentence contents;
	private int current;

	public SentenceContext(Sentence contents) {
		this.contents = contents;
		current = -1;
	}

	public Token getToken(int offset) {
		try {
			return contents.get(current+offset);
		} catch (SentenceIndexOutOfBoundsException e) {
			return NULL_TOKEN;
		}
	}

	@Override
	public SentenceContext clone() {
		try {
			return (SentenceContext) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new Error("Object#clone() threw CloneNotSupportedException", e);
		}
	}

	public boolean hasNext() {
		return current + 1 < contents.size();
	}

	public Token next() {
		// next() should advance BEFORE since we must
		// retain state AFTER invocation
		// (the just returned Token is the current one)
		try {
			return contents.get(++current);
		} catch (SentenceIndexOutOfBoundsException e) {
			throw new NoSuchElementException();
		}
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		Token current = getToken(0);
		
		if (current == NULL_TOKEN)
			result.append("><");
		
		for (Token token : contents) {
			if (token == current)
				result.append(">");
			
			result.append(token.toString());
			
			if (token == current)
				result.append("<");
			
			result.append(" ");
		}
		
		result.setLength(result.length() - 1);
		
		return result.toString();
	}

	
}
