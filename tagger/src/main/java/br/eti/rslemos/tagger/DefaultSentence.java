package br.eti.rslemos.tagger;

import java.util.Arrays;
import java.util.Iterator;

public class DefaultSentence<T> implements Sentence<T> {
	private final Token<T>[] sentence;

	public DefaultSentence(Token<T>... sentence) {
		this.sentence = sentence;
	}

	public Iterator<Token<T>> iterator() {
		return Arrays.asList(sentence).iterator();
	}

	public int size() {
		return sentence.length;
	}

	public Token<T> get(int i) {
		try {
			return sentence[i];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new SentenceIndexOutOfBoundsException(i);
		}
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("\"");
		
		for (Token<T> token : sentence) {
			result.append(token.toString()).append(" ");
		}
		
		result.setLength(result.length() - 1);
		
		result.append("\"");
		
		return result.toString();
	}
	
	
}
