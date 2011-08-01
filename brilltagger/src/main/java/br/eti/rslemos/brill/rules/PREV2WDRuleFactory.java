package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREV2WDRuleFactory extends AbstractSingleRuleFactory {
	public static final RuleFactory INSTANCE = new PREV2WDRuleFactory();

	@Override
	public Rule createRule(Object from, Object to, Context context) {
		String word_2 = context.getToken(-2).getWord();

		return PREV2WDRule.createRule(from, to, word_2);
	}
}