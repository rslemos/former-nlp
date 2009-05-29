package br.eti.rslemos.brill;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import br.eti.rslemos.brill.rules.RuleCreationException;
import br.eti.rslemos.brill.rules.RuleFactory;

public class RuleFactoryStrategy implements RuleProducingStrategy {
	private final List<RuleFactory> ruleFactories;

	public RuleFactoryStrategy(List<RuleFactory> ruleFactories) {
		this.ruleFactories = ruleFactories;
	}

	public Collection<Rule> produceAllPossibleRules(Context context,
			Token target) {
		try {
			Rule[] rules = new Rule[ruleFactories.size()];

			int i = 0;
			for (RuleFactory factory : ruleFactories)
				rules[i++] = factory.create(context, target);

			return Arrays.asList(rules);
		} catch (RuleCreationException e) {
			throw new RuntimeException("Error creating rule", e);
		}
	}
}