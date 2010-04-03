package br.eti.rslemos.brill.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import br.eti.rslemos.brill.DelayedContext;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.RuleBasedTagger;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Token;

@SuppressWarnings("unchecked")
public aspect RuleBasedTaggerObserver extends RuleBasedTaggerEvents {
	
	private List<RuleBasedTaggerListener> RuleBasedTagger.listeners = new ArrayList<RuleBasedTaggerListener>();
	
	public void RuleBasedTagger.addRuleBasedTaggerListener(RuleBasedTaggerListener listener) {
		listeners.add(listener);
	}
	
	public void RuleBasedTagger.removeRuleBasedTaggerListener(RuleBasedTaggerListener listener) {
		listeners.remove(listener);
	}

	private void RuleBasedTagger.fireNotification(Method method, RuleBasedTaggerEvent prototype) {
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
	private static final Method ADVANCE;
	private static final Method COMMIT;
	
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
			ADVANCE = clazz.getMethod("advance", args);
			COMMIT = clazz.getMethod("commit", args);
		} catch (Exception e) {
			throw (Error)(new LinkageError().initCause(e));
		}
	}
	
	before(RuleBasedTagger tagger, Sentence sentence): onTagSentence(tagger, sentence) {
		RuleBasedTaggerEvent prototype = new RuleBasedTaggerEvent(tagger);
		prototype.setOnSentence(sentence);
		
		tagger.fireNotification(TAGGINGSENTENCE, prototype);
	}

	after(RuleBasedTagger tagger, Sentence sentence) returning: onTagSentence(tagger, sentence) {
		RuleBasedTaggerEvent prototype = new RuleBasedTaggerEvent(tagger);
		prototype.setOnSentence(sentence);
	
		tagger.fireNotification(SENTENCETAGGED, prototype);
	}

	before(RuleBasedTagger tagger, Sentence sentence): onBaseTagger(tagger, *, sentence) {
		RuleBasedTaggerEvent prototype = new RuleBasedTaggerEvent(tagger);
		prototype.setOnSentence(sentence);
	
		tagger.fireNotification(BEFOREBASETAGGER, prototype);
	}

	after(RuleBasedTagger tagger, Sentence sentence) returning: onBaseTagger(tagger, *, sentence) {
		RuleBasedTaggerEvent prototype = new RuleBasedTaggerEvent(tagger);
		prototype.setOnSentence(sentence);
	
		tagger.fireNotification(AFTERBASETAGGER, prototype);
	}

	before(RuleBasedTagger tagger, Rule rule, Sentence sentence): onRuleApplication(tagger, rule, sentence) {
		RuleBasedTaggerEvent prototype = new RuleBasedTaggerEvent(tagger);
		prototype.setOnSentence(sentence);
		prototype.setActingRule(rule);

		tagger.fireNotification(BEFORERULEAPPLICATION, prototype);
	}

	after(RuleBasedTagger tagger, Rule rule, Sentence sentence) returning: onRuleApplication(tagger, rule, sentence) {
		RuleBasedTaggerEvent prototype = new RuleBasedTaggerEvent(tagger);
		prototype.setOnSentence(sentence);
		prototype.setActingRule(rule);

		tagger.fireNotification(AFTERRULEAPPLICATION, prototype);
	}

	after(RuleBasedTagger tagger, Rule rule, Sentence sentence, DelayedContext context) returning (Token token): 
		onContextAdvance(tagger, rule, sentence, context) {
		RuleBasedTaggerEvent prototype = new RuleBasedTaggerEvent(tagger);
		prototype.setOnSentence(sentence);
		prototype.setActingRule(rule);
		prototype.setContext(context);
		prototype.setToken(token);

		tagger.fireNotification(ADVANCE, prototype);
	}
	
	after(RuleBasedTagger tagger, Rule rule, Sentence sentence, DelayedContext context) returning: 
		onContextCommit(tagger, rule, sentence, context) {
		RuleBasedTaggerEvent prototype = new RuleBasedTaggerEvent(tagger);
		prototype.setOnSentence(sentence);
		prototype.setActingRule(rule);
		prototype.setContext(context);

		tagger.fireNotification(COMMIT, prototype);
	}

}
