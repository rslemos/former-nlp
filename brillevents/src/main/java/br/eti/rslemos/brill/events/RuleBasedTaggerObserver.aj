package br.eti.rslemos.brill.events;

import java.util.ArrayList;
import java.util.List;

import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.RuleBasedTagger;
import br.eti.rslemos.tagger.Sentence;

public aspect RuleBasedTaggerObserver extends RuleBasedTaggerEvents perthis(this(RuleBasedTagger+)) {
	
	private List<RuleBasedTaggerListener> listeners = new ArrayList<RuleBasedTaggerListener>();
	
	public void RuleBasedTagger.addRuleBasedTaggerListener(RuleBasedTaggerListener listener) {
		aspectOf(this).listeners.add(listener);
	}
	
	public void RuleBasedTagger.removeRuleBasedTaggerListener(RuleBasedTaggerListener listener) {
		aspectOf(this).listeners.remove(listener);
	}

	before(RuleBasedTagger tagger, Sentence sentence): onTagSentence(tagger, sentence) {
		RuleBasedTaggerEvent prototype = new RuleBasedTaggerEvent(tagger);
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
		RuleBasedTaggerEvent prototype = new RuleBasedTaggerEvent(tagger);
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

	before(RuleBasedTagger tagger, Sentence sentence): onBaseTagger(tagger, *, sentence) {
		RuleBasedTaggerEvent prototype = new RuleBasedTaggerEvent(tagger);
		prototype.setOnSentence(sentence);
	
		for (RuleBasedTaggerListener listener : listeners) {
			RuleBasedTaggerEvent event = (RuleBasedTaggerEvent) prototype.clone();
			try {
				listener.beforeBaseTagger(event);
			} catch (Throwable t) {
				// swallow
			}
		}
	}

	after(RuleBasedTagger tagger, Sentence sentence) returning: onBaseTagger(tagger, *, sentence) {
		RuleBasedTaggerEvent prototype = new RuleBasedTaggerEvent(tagger);
		prototype.setOnSentence(sentence);
	
		for (RuleBasedTaggerListener listener : listeners) {
			RuleBasedTaggerEvent event = (RuleBasedTaggerEvent) prototype.clone();
			try {
				listener.afterBaseTagger(event);
			} catch (Throwable t) {
				// swallow
			}
		}
	}

	before(RuleBasedTagger tagger, Rule rule, Sentence sentence): onRuleApplication(tagger, rule, sentence) {
		RuleBasedTaggerEvent prototype = new RuleBasedTaggerEvent(tagger);
		prototype.setOnSentence(sentence);
		prototype.setActingRule(rule);
	
		for (RuleBasedTaggerListener listener : listeners) {
			RuleBasedTaggerEvent event = (RuleBasedTaggerEvent) prototype.clone();
			try {
				listener.beforeRuleApplication(event);
			} catch (Throwable t) {
				// swallow
			}
		}
	}

	after(RuleBasedTagger tagger, Rule rule, Sentence sentence) returning: onRuleApplication(tagger, rule, sentence) {
		RuleBasedTaggerEvent prototype = new RuleBasedTaggerEvent(tagger);
		prototype.setOnSentence(sentence);
		prototype.setActingRule(rule);

		for (RuleBasedTaggerListener listener : listeners) {
			RuleBasedTaggerEvent event = (RuleBasedTaggerEvent) prototype.clone();
			try {
				listener.afterRuleApplication(event);
			} catch (Throwable t) {
				// swallow
			}
		}
	}

}
