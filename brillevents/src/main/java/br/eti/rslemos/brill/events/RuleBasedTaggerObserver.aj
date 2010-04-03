package br.eti.rslemos.brill.events;

import java.util.ArrayList;
import java.util.List;

import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.RuleBasedTagger;
import br.eti.rslemos.tagger.Sentence;

@SuppressWarnings("unchecked")
public aspect RuleBasedTaggerObserver extends RuleBasedTaggerEvents perthis(this(RuleBasedTagger+)) {
	
	private List<RuleBasedTaggerListener> listeners = new ArrayList<RuleBasedTaggerListener>();
	
	public void RuleBasedTagger.addRuleBasedTaggerListener(RuleBasedTaggerListener listener) {
		aspectOf(this).listeners.add(listener);
	}
	
	public void RuleBasedTagger.removeRuleBasedTaggerListener(RuleBasedTaggerListener listener) {
		aspectOf(this).listeners.remove(listener);
	}

	private static interface Method<T, A> {
		void invoke(T target, A argument);
	}
	
	private void fireNotification(Method<RuleBasedTaggerListener, RuleBasedTaggerEvent> method, RuleBasedTaggerEvent prototype) {
		for (RuleBasedTaggerListener listener : listeners) {
			RuleBasedTaggerEvent event = (RuleBasedTaggerEvent) prototype.clone();
			try {
				method.invoke(listener, event);
			} catch (Throwable t) {
				// swallow
			}
		}
	}
	
	private static final Method<RuleBasedTaggerListener, RuleBasedTaggerEvent> TAGGINGSENTENCE =
		new Method<RuleBasedTaggerListener, RuleBasedTaggerEvent>() {
			public void invoke(RuleBasedTaggerListener target, RuleBasedTaggerEvent argument) {
				target.taggingSentence(argument);
			}
	};
	
	private static final Method<RuleBasedTaggerListener, RuleBasedTaggerEvent> SENTENCETAGGED =
		new Method<RuleBasedTaggerListener, RuleBasedTaggerEvent>() {
			public void invoke(RuleBasedTaggerListener target, RuleBasedTaggerEvent argument) {
				target.sentenceTagged(argument);
			}
	};
	
	private static final Method<RuleBasedTaggerListener, RuleBasedTaggerEvent> BEFOREBASETAGGER =
		new Method<RuleBasedTaggerListener, RuleBasedTaggerEvent>() {
			public void invoke(RuleBasedTaggerListener target, RuleBasedTaggerEvent argument) {
				target.beforeBaseTagger(argument);
			}
	};
	
	private static final Method<RuleBasedTaggerListener, RuleBasedTaggerEvent> AFTERBASETAGGER =
		new Method<RuleBasedTaggerListener, RuleBasedTaggerEvent>() {
			public void invoke(RuleBasedTaggerListener target, RuleBasedTaggerEvent argument) {
				target.afterBaseTagger(argument);
			}
	};
	
	private static final Method<RuleBasedTaggerListener, RuleBasedTaggerEvent> BEFORERULEAPPLICATION =
		new Method<RuleBasedTaggerListener, RuleBasedTaggerEvent>() {
			public void invoke(RuleBasedTaggerListener target, RuleBasedTaggerEvent argument) {
				target.beforeRuleApplication(argument);
			}
	};
	
	private static final Method<RuleBasedTaggerListener, RuleBasedTaggerEvent> AFTERRULEAPPLICATION =
		new Method<RuleBasedTaggerListener, RuleBasedTaggerEvent>() {
			public void invoke(RuleBasedTaggerListener target, RuleBasedTaggerEvent argument) {
				target.afterRuleApplication(argument);
			}
	};
	
	before(RuleBasedTagger tagger, Sentence sentence): onTagSentence(tagger, sentence) {
		RuleBasedTaggerEvent prototype = new RuleBasedTaggerEvent(tagger);
		prototype.setOnSentence(sentence);
		
		fireNotification(TAGGINGSENTENCE, prototype);
	}

	after(RuleBasedTagger tagger, Sentence sentence) returning: onTagSentence(tagger, sentence) {
		RuleBasedTaggerEvent prototype = new RuleBasedTaggerEvent(tagger);
		prototype.setOnSentence(sentence);
	
		fireNotification(SENTENCETAGGED, prototype);
	}

	before(RuleBasedTagger tagger, Sentence sentence): onBaseTagger(tagger, *, sentence) {
		RuleBasedTaggerEvent prototype = new RuleBasedTaggerEvent(tagger);
		prototype.setOnSentence(sentence);
	
		fireNotification(BEFOREBASETAGGER, prototype);
	}

	after(RuleBasedTagger tagger, Sentence sentence) returning: onBaseTagger(tagger, *, sentence) {
		RuleBasedTaggerEvent prototype = new RuleBasedTaggerEvent(tagger);
		prototype.setOnSentence(sentence);
	
		fireNotification(AFTERBASETAGGER, prototype);
	}

	before(RuleBasedTagger tagger, Rule rule, Sentence sentence): onRuleApplication(tagger, rule, sentence) {
		RuleBasedTaggerEvent prototype = new RuleBasedTaggerEvent(tagger);
		prototype.setOnSentence(sentence);
		prototype.setActingRule(rule);

		fireNotification(BEFORERULEAPPLICATION, prototype);
	}

	after(RuleBasedTagger tagger, Rule rule, Sentence sentence) returning: onRuleApplication(tagger, rule, sentence) {
		RuleBasedTaggerEvent prototype = new RuleBasedTaggerEvent(tagger);
		prototype.setOnSentence(sentence);
		prototype.setActingRule(rule);

		fireNotification(AFTERRULEAPPLICATION, prototype);
	}

}
