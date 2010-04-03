package br.eti.rslemos.brill.events;

import br.eti.rslemos.brill.BrillTagger;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Tagger;
import br.eti.rslemos.brill.DelayedContext;
import br.eti.rslemos.tagger.Token;
import br.eti.rslemos.brill.Context;

@SuppressWarnings("unchecked")
public abstract privileged aspect BrillTaggerPointcuts {
	
	@SuppressWarnings("unchecked")
	public pointcut onTagSentence(BrillTagger tagger, Sentence sentence):
		this(tagger) && 
		execution(public void BrillTagger+.tag(Sentence+)) && args(sentence) &&
		within(BrillTagger+);
	
	@SuppressWarnings("unchecked")
	public pointcut onBaseTagger(BrillTagger tagger, Tagger baseTagger, Sentence sentence):
		call(void Tagger+.tag(Sentence+)) && target(baseTagger) &&
		cflow(onTagSentence(tagger, sentence)) && within(BrillTagger+);

	@SuppressWarnings("unchecked")
	private pointcut _onRuleApplication(BrillTagger tagger, Rule rule, Sentence sentence, DelayedContext context):
		call(void BrillTagger.applyRule(DelayedContext+, Rule+)) && args(context, rule) &&
		cflow(onTagSentence(tagger, sentence)) && within(BrillTagger+);
		
	@SuppressWarnings("unchecked")
	public pointcut onRuleApplication(BrillTagger tagger, Rule rule, Sentence sentence):
		_onRuleApplication(tagger, rule, sentence, *);
	
	@SuppressWarnings("unchecked")
	public pointcut onContextAdvance(BrillTagger tagger, Rule rule, Sentence sentence, DelayedContext context):
		call(Token+ DelayedContext+.next()) && target(context) &&
		cflow(_onRuleApplication(tagger, rule, sentence, *)) && within(BrillTagger+);
	
	@SuppressWarnings("unchecked")
	public pointcut onContextCommit(BrillTagger tagger, Rule rule, Sentence sentence, DelayedContext context):
		call(void DelayedContext+.commit()) && target(context) &&
		cflow(_onRuleApplication(tagger, rule, sentence, *)) && within(BrillTagger+);

	@SuppressWarnings("unchecked")
	public pointcut onContextualRuleApplication(BrillTagger tagger, Rule rule, Sentence sentence, Context context):
		call(boolean Rule+.apply(Context)) && target(rule) && args(context) &&
		cflow(_onRuleApplication(tagger, *, sentence, *)) && within(BrillTagger+);
}

