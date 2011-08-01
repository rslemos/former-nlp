package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXTWDRuleFactory extends AbstractSingleRuleFactory {
	public static final RuleFactory INSTANCE = new NEXTWDRuleFactory();

	@Override
	public Rule createRule(Object from, Object to, Context context) {
		String word1 = context.getToken(1).getWord();

		return NEXTWDRule.createRule(from, to, word1);
	}
}