package br.eti.rslemos.brill;

import java.util.List;
import java.util.Set;

import br.eti.rslemos.brill.RuleBasedTagger.BufferingContext;

public interface RuleSelectStrategy {
	public static interface RuleSelectStrategyContext {
		Rule selectBestRule(Set<Rule> possibleRules);
	}
	
	RuleSelectStrategy.RuleSelectStrategyContext getContext(List<List<Token>> proofCorpus, BufferingContext[] trainingCorpus);
}