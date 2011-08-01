package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDNEXTTAGRuleFactory extends AbstractSingleRuleFactory {
	public static final RuleFactory INSTANCE = new WDNEXTTAGRuleFactory();

	@Override
	public Rule createRule(Object from, Object to, Context context) {
		String word0 = context.getToken(0).getWord();
		Object tag1 = context.getToken(1).getTag();

		return WDNEXTTAGRule.createRule(from, to, word0, tag1);
	}
}