package br.eti.rslemos.tagger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CompositeTagger implements Tagger, Serializable {

	private static final long serialVersionUID = -8583142580025744638L;

	private Tagger[] taggers;

	public CompositeTagger() {
		this(new Tagger[0]);
	}

	public CompositeTagger(Tagger... taggers) {
		this.taggers = taggers;
	}

	public void tag(Sentence sentence) {
		sentence = wrapSentence(sentence);

		for (Tagger tagger : taggers) {
			tagger.tag(sentence);
		}
	}

	private Sentence wrapSentence(Sentence sentence) {
		ArrayList<Token> wrappedSentence = new ArrayList<Token>(sentence.size());
		
		for (Token token : sentence) {
			wrappedSentence.add(new FilteringToken(token));
		}
		
		return new DefaultSentence(wrappedSentence);
	}
	
	public Tagger[] getTaggers() {
		return taggers;
	}

	public void setTaggers(Tagger[] taggers) {
		this.taggers = taggers;
	}

	private static final class FilteringToken implements Token {
		private final Token token;
		private Set<String> alreadySet = new HashSet<String>();

		private FilteringToken(Token token) {
			this.token = token;
		}

		public Object getFeature(String name) {
			return token.getFeature(name);
		}

		public FilteringToken setFeature(String name, Object value) {
			if (!alreadySet.contains(name)) {
				token.setFeature(name, value);
				alreadySet.add(name);
			}
			return this;
		}

		public Map<String, Object> getFeatures() {
			return token.getFeatures();
		}
	}


}
