package br.eti.rslemos.brill.events;

import java.util.EventObject;

import br.eti.rslemos.brill.RuleBasedTagger;
import br.eti.rslemos.tagger.Sentence;

public class RuleBasedTaggerEvent<T> extends EventObject implements Cloneable {

	private Sentence<T> onSentence;

	public RuleBasedTaggerEvent(RuleBasedTagger<T> tagger) {
		super(tagger);
	}

	public void setOnSentence(Sentence<T> sentence) {
		this.onSentence = sentence;
	}

	public Sentence<T> getOnSentence() {
		return onSentence;
	}

	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null)
			return false;

		if (this.getClass() != o.getClass())
			return false;

		RuleBasedTaggerEvent other = (RuleBasedTaggerEvent)o;
		
		return
			(source != null ? source.equals(other.source) : other.source == null) &&
			(onSentence != null ? onSentence.equals(other.onSentence) : other.onSentence == null);
	}

	@Override
	public int hashCode() {
		int hashCode = 0;
		
		hashCode += source != null ? source.hashCode() : 0;
		hashCode *= 3;
		hashCode += onSentence != null ? onSentence.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
	
}
