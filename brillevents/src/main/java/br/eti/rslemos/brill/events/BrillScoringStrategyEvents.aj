package br.eti.rslemos.brill.events;

import java.util.List;

import org.apache.commons.lang.ObjectUtils;

import br.eti.rslemos.brill.BrillScoringStrategy;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.Token;
import br.eti.rslemos.brill.RuleBasedTagger.BufferingContext;
import br.eti.rslemos.brill.RulesetTrainer.TrainingContext;

import br.eti.rslemos.brill.ConstantTokenTagger;;

public abstract aspect BrillScoringStrategyEvents {
	
	protected pointcut setTrainingContext(BrillScoringStrategy thiz, TrainingContext context):
		execution(void BrillScoringStrategy.setTrainingContext(TrainingContext)) && args(context) && this(thiz) &&
		within(BrillScoringStrategy);

	protected pointcut computeRule(BrillScoringStrategy thiz, Rule rule):
		execution(int BrillScoringStrategy.compute(Rule)) && args(rule) && this(thiz) &&
		within(BrillScoringStrategy);

	protected pointcut computeSentence(BrillScoringStrategy thiz, Rule rule, List<Token> proofSentence, BufferingContext trainingSentence):
		execution(int BrillScoringStrategy.compute(Rule, List<Token>, BufferingContext)) && args(rule, proofSentence, trainingSentence) && this(thiz) &&
		within(BrillScoringStrategy);

	private pointcut insideCompute(BrillScoringStrategy thiz, Rule rule):
		cflow(computeRule(thiz, rule)) && 
		withincode(int BrillScoringStrategy.compute(Rule));
	
	protected pointcut ruleScored(BrillScoringStrategy thiz, Rule rule, String from, String to):
		call(boolean ObjectUtils.equals(Object, Object)) && args(to, from) && insideCompute(thiz, rule);
	
}
