package br.eti.rslemos.brill;

import java.util.List;
import java.util.Set;

import br.eti.rslemos.brill.RuleBasedTagger.BufferingContext;

public class ScoringRuleSelectStrategy implements RuleSelectStrategy {
	private final ScoringStrategy scoringStrategy;

	public ScoringRuleSelectStrategy(ScoringStrategy scoringStrategy) {
		this.scoringStrategy = scoringStrategy;
	}

	public RuleSelectStrategyContext getContext(final List<List<Token>> proofCorpus, final BufferingContext[] trainingCorpus) {
		return new RuleSelectStrategyContext() {
	
			public Rule selectBestRule(Set<Rule> possibleRules) {
				Rule bestRule = null;
				int bestScore = 0;
	
				for (Rule rule : possibleRules) {
	
					int score = scoringStrategy.compute(proofCorpus,
							trainingCorpus, rule);
	
					if (score > bestScore) {
						bestRule = rule;
						bestScore = score;
					}
				}
	
				return bestRule;
			}
		};
	}
}
