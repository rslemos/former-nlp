package br.eti.rslemos.brill.events;

import br.eti.rslemos.brill.RuleBasedTagger;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Tagger;

@SuppressWarnings("unchecked")
public abstract privileged aspect RuleBasedTaggerEvents {
	
	@SuppressWarnings("unchecked")
	public pointcut onTagSentence(RuleBasedTagger tagger, Sentence sentence):
		this(tagger) && 
		execution(public void RuleBasedTagger+.tag(Sentence+)) && args(sentence) &&
		within(RuleBasedTagger+);
	
	@SuppressWarnings("unchecked")
	public pointcut onBaseTagger(RuleBasedTagger tagger, Tagger baseTagger, Sentence sentence):
		call(void Tagger+.tag(Sentence+)) && target(baseTagger) &&
		cflow(onTagSentence(tagger, sentence)) && within(RuleBasedTagger+);

	@SuppressWarnings("unchecked")
	public pointcut onRuleApplication(RuleBasedTagger tagger, Rule rule, Sentence sentence):
		call(void RuleBasedTagger+.applyRule(*, Rule)) && args(*, rule) &&
		cflow(onTagSentence(tagger, sentence)) && within(RuleBasedTagger+);
}

