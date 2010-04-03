package br.eti.rslemos.brill.events;

import java.util.EventListener;

public interface RuleBasedTaggerListener<T> extends EventListener {

	void taggingSentence(RuleBasedTaggerEvent<T> event);

	void sentenceTagged(RuleBasedTaggerEvent<T> event);

}
