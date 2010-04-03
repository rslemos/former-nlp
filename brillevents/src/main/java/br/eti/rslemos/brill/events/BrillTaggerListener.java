package br.eti.rslemos.brill.events;

import java.util.EventListener;

public interface BrillTaggerListener extends EventListener {

	void taggingSentence(BrillTaggerEvent event);

	void sentenceTagged(BrillTaggerEvent event);

	void beforeBaseTagger(BrillTaggerEvent event);

	void afterBaseTagger(BrillTaggerEvent event);

	void beforeRuleApplication(BrillTaggerEvent event);

	void afterRuleApplication(BrillTaggerEvent event);

	void advance(BrillTaggerEvent event);

	void commit(BrillTaggerEvent event);

	void ruleApplied(BrillTaggerEvent event);

}
