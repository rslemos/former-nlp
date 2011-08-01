package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXTTAGRuleFactory extends AbstractSingleRuleFactory {
	public static final RuleFactory INSTANCE = new NEXTTAGRuleFactory();

	@Override
	public Rule createRule(Object from, Object to, Context context) {
		Object tag1 = context.getToken(1).getTag();

		return NEXTTAGRule.createRule(from, to, tag1);
	}
}