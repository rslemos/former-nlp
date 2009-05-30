package br.eti.rslemos.brill.events;

import org.apache.commons.lang.ObjectUtils;

import br.eti.rslemos.brill.BrillScoringStrategy;
import br.eti.rslemos.brill.Rule;

public abstract aspect BrillScoringStrategyEvents {

	private pointcut compute(Rule rule):
		cflow(execution(int BrillScoringStrategy.compute(Rule)) && args(rule)) && 
		withincode(int BrillScoringStrategy.compute(Rule));
	
	protected pointcut ruleScored(Rule rule, String from, String to):
		call(boolean ObjectUtils.equals(Object, Object)) && args(to, from) && compute(rule);
}
