package br.eti.rslemos.brill.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.DelayedContext;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.BrillTagger;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Token;

public aspect BrillTaggerObserver extends BrillTaggerPointcuts {
	private List<BrillTaggerListener> BrillTagger.listeners = new ArrayList<BrillTaggerListener>();
	
	public void BrillTagger.addBrillTaggerListener(BrillTaggerListener listener) {
		listeners.add(listener);
	}
	
	public void BrillTagger.removeBrillTaggerListener(BrillTaggerListener listener) {
		listeners.remove(listener);
	}

	private void BrillTagger.fireNotification(Method method, BrillTaggerEvent prototype) {
		for (BrillTaggerListener listener : listeners) {
			BrillTaggerEvent event = (BrillTaggerEvent) prototype.clone();
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
	private static final Method RULEAPPLIED;
	
	static {
		Class<BrillTaggerListener> clazz = BrillTaggerListener.class;
		Class<?>[] args = new Class[] {BrillTaggerEvent.class};
		
		try {
			TAGGINGSENTENCE = clazz.getMethod("taggingSentence", args);
			SENTENCETAGGED = clazz.getMethod("sentenceTagged", args);
			BEFOREBASETAGGER = clazz.getMethod("beforeBaseTagger", args);
			AFTERBASETAGGER = clazz.getMethod("afterBaseTagger", args);
			BEFORERULEAPPLICATION = clazz.getMethod("beforeRuleApplication", args);
			AFTERRULEAPPLICATION = clazz.getMethod("afterRuleApplication", args);
			ADVANCE = clazz.getMethod("advance", args);
			COMMIT = clazz.getMethod("commit", args);
			RULEAPPLIED = clazz.getMethod("ruleApplied", args);
		} catch (Exception e) {
			throw (Error)(new LinkageError().initCause(e));
		}
	}
	
	before(BrillTagger tagger, Sentence sentence): onTagSentence(tagger, sentence) {
		BrillTaggerEvent prototype = new BrillTaggerEvent(tagger);
		prototype.setOnSentence(sentence);
		
		tagger.fireNotification(TAGGINGSENTENCE, prototype);
	}

	after(BrillTagger tagger, Sentence sentence) returning: onTagSentence(tagger, sentence) {
		BrillTaggerEvent prototype = new BrillTaggerEvent(tagger);
		prototype.setOnSentence(sentence);
	
		tagger.fireNotification(SENTENCETAGGED, prototype);
	}

	before(BrillTagger tagger, Sentence sentence): onBaseTagger(tagger, *, sentence) {
		BrillTaggerEvent prototype = new BrillTaggerEvent(tagger);
		prototype.setOnSentence(sentence);
	
		tagger.fireNotification(BEFOREBASETAGGER, prototype);
	}

	after(BrillTagger tagger, Sentence sentence) returning: onBaseTagger(tagger, *, sentence) {
		BrillTaggerEvent prototype = new BrillTaggerEvent(tagger);
		prototype.setOnSentence(sentence);
	
		tagger.fireNotification(AFTERBASETAGGER, prototype);
	}

	before(BrillTagger tagger, Rule rule, Sentence sentence): onRuleApplication(tagger, rule, sentence) {
		BrillTaggerEvent prototype = new BrillTaggerEvent(tagger);
		prototype.setOnSentence(sentence);
		prototype.setActingRule(rule);

		tagger.fireNotification(BEFORERULEAPPLICATION, prototype);
	}

	after(BrillTagger tagger, Rule rule, Sentence sentence) returning: onRuleApplication(tagger, rule, sentence) {
		BrillTaggerEvent prototype = new BrillTaggerEvent(tagger);
		prototype.setOnSentence(sentence);
		prototype.setActingRule(rule);

		tagger.fireNotification(AFTERRULEAPPLICATION, prototype);
	}

	after(BrillTagger tagger, Rule rule, Sentence sentence, DelayedContext context) returning (Token token): 
		onContextAdvance(tagger, rule, sentence, context) {
		BrillTaggerEvent prototype = new BrillTaggerEvent(tagger);
		prototype.setOnSentence(sentence);
		prototype.setActingRule(rule);
		prototype.setContext(context);
		prototype.setToken(token);

		tagger.fireNotification(ADVANCE, prototype);
	}
	
	after(BrillTagger tagger, Rule rule, Sentence sentence, DelayedContext context) returning: 
		onContextCommit(tagger, rule, sentence, context) {
		BrillTaggerEvent prototype = new BrillTaggerEvent(tagger);
		prototype.setOnSentence(sentence);
		prototype.setActingRule(rule);
		prototype.setContext(context);

		tagger.fireNotification(COMMIT, prototype);
	}

	after(BrillTagger tagger, Rule rule, Sentence sentence, Context context) returning (boolean result):
		onContextualRuleApplication(tagger, rule, sentence, context) {
		
		BrillTaggerEvent prototype = new BrillTaggerEvent(tagger);
		prototype.setOnSentence(sentence);
		prototype.setActingRule(rule);
		prototype.setContext(context);
		prototype.setRuleApplies(result);

		tagger.fireNotification(RULEAPPLIED, prototype);
	}
}
