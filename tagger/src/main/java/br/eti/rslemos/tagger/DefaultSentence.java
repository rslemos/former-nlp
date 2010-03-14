package br.eti.rslemos.tagger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DefaultSentence<T> implements Sentence<T> {
	private final List<Token<T>> sentence;

	public DefaultSentence(List<Token<T>> sentence) {
		this.sentence = sentence;
	}

	public DefaultSentence(Sentence<T> sentence) {
		this.sentence = new ArrayList<Token<T>>(sentence.size());
		for (Token<T> token : sentence) {
			this.sentence.add(new DefaultToken<T>(token));
		}
		
		((ArrayList<Token<T>>)this.sentence).trimToSize();
	}

	public Iterator<Token<T>> iterator() {
		return sentence.iterator();
	}

	public int size() {
		return sentence.size();
	}

	public Token<T> get(int i) {
		try {
			return sentence.get(i);
		} catch (IndexOutOfBoundsException e) {
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
