package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREVTAGRuleFactory extends AbstractSingleRuleFactory {
	public static final RuleFactory INSTANCE = new PREVTAGRuleFactory();

	@Override
	public Rule createRule(Object from, Object to, Context context) {
		Object tag_1 = context.getToken(-1).getTag();

		return PREVTAGRule.createRule(from, to, tag_1);
	}
}