package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDAND2TAGAFTRuleFactory extends AbstractSingleRuleFactory {
	public static final RuleFactory INSTANCE = new WDAND2TAGAFTRuleFactory();

	@Override
	public Rule createRule(Object from, Object to, Context context) {
		String word0 = context.getToken(0).getWord();
		Object tag2 = context.getToken(2).getTag();

		return new WDAND2TAGAFTRule(from, to, word0, tag2);
	}
}