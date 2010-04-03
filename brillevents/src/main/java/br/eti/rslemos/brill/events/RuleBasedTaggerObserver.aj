package br.eti.rslemos.brill.events;

import java.util.ArrayList;
import java.util.List;

import br.eti.rslemos.brill.RuleBasedTagger;
import br.eti.rslemos.tagger.Sentence;

public aspect RuleBasedTaggerObserver extends RuleBasedTaggerEvents perthis(this(RuleBasedTagger+)) {
	
	private List<RuleBasedTaggerListener> listeners = new ArrayList<RuleBasedTaggerListener>();
	
	public RuleBasedTaggerObserver() {
		System.out.println("Aspect ctor!!!!");
	}
	
	public void RuleBasedTagger.addRuleBasedTaggerListener(RuleBasedTaggerListener listener) {
		aspectOf(this).listeners.add(listener);
	}
	
	public void RuleBasedTagger.removeRuleBasedTaggerListener(RuleBasedTaggerListener listener) {
		aspectOf(this).listeners.remove(listener);
	}

	before(RuleBasedTagger tagger, Sentence sentence): onTagSentence(tagger, sentence) {
		RuleBasedTaggerEvent prototype = new RuleBasedTaggerEvent((RuleBasedTagger)thisJoinPoint.getThis());
		prototype.setOnSentence(sentence);
	
		for (RuleBasedTaggerListener listener : listeners) {
			RuleBasedTaggerEvent event = (RuleBasedTaggerEvent) prototype.clone();
			try {
				listener.taggingSentence(event);
			} catch (Throwable t) {
				// swallow
			}
		}
	}

	after(RuleBasedTagger tagger, Sentence sentence) returning: onTagSentence(tagger, sentence) {
		RuleBasedTaggerEvent prototype = new RuleBasedTaggerEvent((RuleBasedTagger)thisJoinPoint.getThis());
		prototype.setOnSentence(sentence);
	
		for (RuleBasedTaggerListener listener : listeners) {
			RuleBasedTaggerEvent event = (RuleBasedTaggerEvent) prototype.clone();
			try {
				listener.sentenceTagged(event);
			} catch (Throwable t) {
				// swallow
			}
		}
	}
}
