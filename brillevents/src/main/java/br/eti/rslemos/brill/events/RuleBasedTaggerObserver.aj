package br.eti.rslemos.brill.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

	private void fireNotification(Method method, RuleBasedTaggerEvent prototype) {
		for (RuleBasedTaggerListener listener : listeners) {
			RuleBasedTaggerEvent event = (RuleBasedTaggerEvent) prototype.clone();
			try {
				method.invoke(listener, event);
			} catch (InvocationTargetException e) {
				// swallow
			} catch (Exception e) {
				throw (Error)(new LinkageError().initCause(e));
			}
		}
	}
	
	private static final Method TAGGINGSENTENCE;
	private static final Method SENTENCETAGGED;
	private static final Method BEFOREBASETAGGER;
	private static final Method AFTERBASETAGGER;
	private static final Method BEFORERULEAPPLICATION;
	private static final Method AFTERRULEAPPLICATION;
	
	static {
		Class<RuleBasedTaggerListener> clazz = RuleBasedTaggerListener.class;
		Class[] args = new Class[] {RuleBasedTaggerEvent.class};
		
		try {
			TAGGINGSENTENCE = clazz.getMethod("taggingSentence", args);
			SENTENCETAGGED = clazz.getMethod("sentenceTagged", args);
			BEFOREBASETAGGER = clazz.getMethod("beforeBaseTagger", args);
			AFTERBASETAGGER = clazz.getMethod("afterBaseTagger", args);
			BEFORERULEAPPLICATION = clazz.getMethod("beforeRuleApplication", args);
			AFTERRULEAPPLICATION = clazz.getMethod("afterRuleApplication", args);
		} catch (Exception e) {
			throw (Error)(new LinkageError().initCause(e));
		}
	}
	
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
