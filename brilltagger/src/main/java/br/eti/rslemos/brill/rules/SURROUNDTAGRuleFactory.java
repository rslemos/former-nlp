package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class SURROUNDTAGRuleFactory extends AbstractSingleRuleFactory {
	@Override
	public Rule createRule(Object from, Object to, Context context) {
		Object tag_1 = context.getToken(-1).getTag();
		Object tag1 = context.getToken(1).getTag();

		return new SURROUNDTAGRule(from, to, tag_1, tag1);
	}
}