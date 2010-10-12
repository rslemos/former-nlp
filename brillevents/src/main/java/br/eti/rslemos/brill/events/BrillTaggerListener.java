package br.eti.rslemos.brill.events;

import java.util.EventListener;

public interface BrillTaggerListener extends EventListener {

	void taggingStart(BrillTaggerEvent event);

	void taggingFinish(BrillTaggerEvent event);

	void ruleApplicationStart(BrillTaggerEvent event);

	void ruleApplicationFinish(BrillTaggerEvent event);

	void contextAdvanced(BrillTaggerEvent event);

	void contextCommitted(BrillTaggerEvent event);

	void ruleApplied(BrillTaggerEvent event);

}
