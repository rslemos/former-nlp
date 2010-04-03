package br.eti.rslemos.brill.events;

import java.util.EventListener;

public interface RuleBasedTaggerListener<T> extends EventListener {

	void taggingSentence(RuleBasedTaggerEvent<T> event);

	void sentenceTagged(RuleBasedTaggerEvent<T> event);

	void beforeBaseTagger(RuleBasedTaggerEvent<T> event);

	void afterBaseTagger(RuleBasedTaggerEvent<T> event);

	void beforeRuleApplication(RuleBasedTaggerEvent<T> event);

	void afterRuleApplication(RuleBasedTaggerEvent<T> event);

	void advance(RuleBasedTaggerEvent<T> event);

	void commit(RuleBasedTaggerEvent<T> event);

	void ruleApplied(RuleBasedTaggerEvent<T> event);

}
