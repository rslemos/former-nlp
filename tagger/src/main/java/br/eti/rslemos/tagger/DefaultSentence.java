package br.eti.rslemos.tagger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DefaultSentence implements Sentence {
	private final List<Token> sentence;

	public DefaultSentence(List<Token> sentence) {
		this.sentence = sentence;
	}

	public DefaultSentence(Sentence sentence) {
		this.sentence = new ArrayList<Token>(sentence.size());
		for (Token token : sentence) {
			this.sentence.add(new DefaultToken(token));
		}
		
		((ArrayList<Token>)this.sentence).trimToSize();
	}

	public Iterator<Token> iterator() {
		return sentence.iterator();
	}

	public int size() {
		return sentence.size();
	}

	public Token get(int i) {
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
		
		for (Token token : sentence) {
			result.append(token.toString()).append(" ");
		}
		
		result.setLength(result.length() - 1);
		
		result.append("\"");
		
		return result.toString();
	}
	
	
}
