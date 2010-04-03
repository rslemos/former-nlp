package br.eti.rslemos.brill.events;

import br.eti.rslemos.brill.RuleBasedTagger;
import br.eti.rslemos.tagger.Sentence;

public abstract privileged aspect RuleBasedTaggerEvents {
	
	public pointcut onTagSentence(RuleBasedTagger tagger, Sentence sentence):
		execution(public void RuleBasedTagger+.tag(Sentence)) && this(tagger) && args(sentence);
	
}
