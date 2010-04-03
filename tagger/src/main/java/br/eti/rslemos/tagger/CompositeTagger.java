package br.eti.rslemos.tagger;

import java.io.Serializable;
import java.util.ArrayList;

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
		private boolean alreadySet = false;

		private FilteringToken(Token token) {
			this.token = token;
		}

		public Object getTag() {
			return token.getTag();
		}

		public String getWord() {
			return token.getWord();
		}

		public void setTag(Object tag) {
			if (!alreadySet) {
				token.setTag(tag);
				alreadySet = true;
			}
		}
	}


}
