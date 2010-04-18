package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDAND2TAGBFRRuleFactory extends AbstractSingleRuleFactory {
	public static final RuleFactory INSTANCE = new WDAND2TAGBFRRuleFactory();

	@Override
	public Rule createRule(Object from, Object to, Context context) {
		Object tag_2 = context.getToken(-2).getTag();
		String word0 = context.getToken(0).getWord();

		return new WDAND2TAGBFRRule(from, to, tag_2, word0);
	}
}