package br.eti.rslemos.brill.events;

import java.util.EventListener;

public interface BrillTaggerListener<T> extends EventListener {

	void taggingSentence(BrillTaggerEvent<T> event);

	void sentenceTagged(BrillTaggerEvent<T> event);

	void beforeBaseTagger(BrillTaggerEvent<T> event);

	void afterBaseTagger(BrillTaggerEvent<T> event);

	void beforeRuleApplication(BrillTaggerEvent<T> event);

	void afterRuleApplication(BrillTaggerEvent<T> event);

	void advance(BrillTaggerEvent<T> event);

	void commit(BrillTaggerEvent<T> event);

	void ruleApplied(BrillTaggerEvent<T> event);

}
