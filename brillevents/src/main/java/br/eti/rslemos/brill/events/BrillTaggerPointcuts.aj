package br.eti.rslemos.brill.events;

import br.eti.rslemos.brill.BrillTagger;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.brill.DelayedContext;
import br.eti.rslemos.tagger.Token;
import br.eti.rslemos.brill.Context;

public abstract privileged aspect BrillTaggerPointcuts {
	
	public pointcut onTagSentence(BrillTagger tagger, Sentence sentence):
		this(tagger) && 
		execution(public void BrillTagger+.tag(Sentence+)) && args(sentence) &&
		within(BrillTagger+);
	
	private pointcut _onRuleApplication(BrillTagger tagger, Rule rule, Sentence sentence, DelayedContext context):
		call(void BrillTagger.applyRule(DelayedContext+, Rule+)) && args(context, rule) &&
		cflow(onTagSentence(tagger, sentence)) && within(BrillTagger+);
		
	public pointcut onRuleApplication(BrillTagger tagger, Rule rule, Sentence sentence):
		_onRuleApplication(tagger, rule, sentence, *);
	
	public pointcut onContextAdvance(BrillTagger tagger, Rule rule, Sentence sentence, Context context):
		call(Token+ Context+.next()) && target(context) &&
		cflow(_onRuleApplication(tagger, rule, sentence, *)) && within(BrillTagger+);
	
	public pointcut onContextCommit(BrillTagger tagger, Rule rule, Sentence sentence, DelayedContext context):
		call(void DelayedContext+.commit()) && target(context) &&
		cflow(_onRuleApplication(tagger, rule, sentence, *)) && within(BrillTagger+);

	public pointcut onContextualRuleApplication(BrillTagger tagger, Rule rule, Sentence sentence, Context context):
		call(boolean Rule+.apply(Context)) && target(rule) && args(context) &&
		cflow(_onRuleApplication(tagger, *, sentence, *)) && within(BrillTagger+);
}

