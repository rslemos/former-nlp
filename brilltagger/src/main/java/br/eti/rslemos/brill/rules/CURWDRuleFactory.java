package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class CURWDRuleFactory extends AbstractSingleRuleFactory {
	public static final RuleFactory INSTANCE = new CURWDRuleFactory();

	@Override
	public Rule createRule(Object from, Object to, Context context) {
		String word0 = context.getToken(0).getWord();

		return new CURWDRule(from, to, word0);
	}
}