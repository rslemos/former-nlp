package br.eti.rslemos.brill.events;

import br.eti.rslemos.brill.RuleBasedTagger;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Tagger;
import br.eti.rslemos.brill.DelayedContext;
import br.eti.rslemos.tagger.Token;
import br.eti.rslemos.brill.Context;

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
	private pointcut _onRuleApplication(RuleBasedTagger tagger, Rule rule, Sentence sentence, DelayedContext context):
		call(void RuleBasedTagger.applyRule(DelayedContext+, Rule+)) && args(context, rule) &&
		cflow(onTagSentence(tagger, sentence)) && within(RuleBasedTagger+);
		
	@SuppressWarnings("unchecked")
	public pointcut onRuleApplication(RuleBasedTagger tagger, Rule rule, Sentence sentence):
		_onRuleApplication(tagger, rule, sentence, *);
	
	@SuppressWarnings("unchecked")
	public pointcut onContextAdvance(RuleBasedTagger tagger, Rule rule, Sentence sentence, DelayedContext context):
		call(Token+ DelayedContext+.next()) && target(context) &&
		cflow(_onRuleApplication(tagger, rule, sentence, *)) && within(RuleBasedTagger+);
	
	@SuppressWarnings("unchecked")
	public pointcut onContextCommit(RuleBasedTagger tagger, Rule rule, Sentence sentence, DelayedContext context):
		call(void DelayedContext+.commit()) && target(context) &&
		cflow(_onRuleApplication(tagger, rule, sentence, *)) && within(RuleBasedTagger+);

	@SuppressWarnings("unchecked")
	public pointcut onContextualRuleApplication(RuleBasedTagger tagger, Rule rule, Sentence sentence, Context context):
		call(boolean Rule+.apply(Context)) && target(rule) && args(context) &&
		cflow(_onRuleApplication(tagger, *, sentence, *)) && within(RuleBasedTagger+);
}

