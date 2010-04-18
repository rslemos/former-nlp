package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class LBIGRAMRuleFactory extends AbstractSingleRuleFactory {
	public static final RuleFactory INSTANCE = new LBIGRAMRuleFactory();

	@Override
	public Rule createRule(Object from, Object to, Context context) {
		String word0 = context.getToken(0).getWord();
		String word_1 = context.getToken(-1).getWord();

		return new LBIGRAMRule(from, to, word_1, word0);
	}
}