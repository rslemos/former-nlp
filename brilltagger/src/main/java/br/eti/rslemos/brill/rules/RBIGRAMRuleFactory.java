package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class RBIGRAMRuleFactory extends AbstractSingleRuleFactory {
	public static final RuleFactory INSTANCE = new RBIGRAMRuleFactory();

	@Override
	public Rule createRule(Object from, Object to, Context context) {
		String word0 = context.getToken(0).getWord();
		String word1 = context.getToken(1).getWord();

		return new RBIGRAMRule(from, to, word0, word1);
	}
}