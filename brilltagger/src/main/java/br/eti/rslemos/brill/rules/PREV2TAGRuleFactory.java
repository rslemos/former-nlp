package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREV2TAGRuleFactory extends AbstractSingleRuleFactory {
	public static final RuleFactory INSTANCE = new PREV2TAGRuleFactory();

	@Override
	public Rule createRule(Object from, Object to, Context context) {
		Object tag_2 = context.getToken(-2).getTag();

		return PREV2TAGRule.createRule(from, to, tag_2);
	}
}