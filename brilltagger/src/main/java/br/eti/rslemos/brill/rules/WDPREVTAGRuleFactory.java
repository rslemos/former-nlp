package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDPREVTAGRuleFactory extends AbstractSingleRuleFactory {
	public static final AbstractRuleFactory INSTANCE = new WDPREVTAGRuleFactory();

	@Override
	public Rule createRule(Object from, Object to, Context context) {
		String word0 = context.getToken(0).getWord();
		Object tag_1 = context.getToken(-1).getTag();

		return new WDPREVTAGRule(from, to, tag_1, word0);
	}
}