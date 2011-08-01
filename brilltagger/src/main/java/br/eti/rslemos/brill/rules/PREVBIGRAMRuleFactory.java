package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREVBIGRAMRuleFactory extends AbstractSingleRuleFactory {
	public static final RuleFactory INSTANCE = new PREVBIGRAMRuleFactory();

	@Override
	public Rule createRule(Object from, Object to, Context context) {
		String word_2 = context.getToken(-2).getWord();
		String word_1 = context.getToken(-1).getWord();

		return PREVBIGRAMRule.createRule(from, to, word_2, word_1);
	}
}