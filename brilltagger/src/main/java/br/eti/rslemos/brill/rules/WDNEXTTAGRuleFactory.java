package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDNEXTTAGRuleFactory extends AbstractSingleRuleFactory {
	@Override
	public Rule createRule(Object from, Object to, Context context) {
		String word0 = context.getToken(0).getWord();
		Object tag1 = context.getToken(1).getTag();

		return new WDNEXTTAGRule(from, to, word0, tag1);
	}
}