package br.eti.rslemos.brill.events;

import br.eti.rslemos.brill.RuleBasedTagger;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Tagger;

public abstract privileged aspect RuleBasedTaggerEvents {
	
	public pointcut onTagSentence(RuleBasedTagger tagger, Sentence sentence):
		this(tagger) && 
		execution(public void RuleBasedTagger+.tag(Sentence+)) && args(sentence) &&
		within(RuleBasedTagger+);
	
	public pointcut onBaseTagger(RuleBasedTagger tagger, Tagger baseTagger, Sentence sentence):
		this(tagger) &&
		call(void Tagger+.tag(Sentence+)) && target(baseTagger) && args(sentence) &&
		withincode(void RuleBasedTagger+.applyBaseTagger(Sentence+)); 
}

