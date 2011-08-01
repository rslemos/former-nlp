package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXT2TAGRuleFactory extends AbstractSingleRuleFactory {
	public static final RuleFactory INSTANCE = new NEXT2TAGRuleFactory();

	@Override
	public Rule createRule(Object from, Object to, Context context) {
		Object tag2 = context.getToken(2).getTag();

		return NEXT2TAGRule.createRule(from, to, tag2);
	}
}