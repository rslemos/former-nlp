package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREVBIGRAMRuleFactory extends AbstractSingleRuleFactory {
	public Rule createRule(Object from, Object to, Context context) {
		String word_2 = context.getToken(-2).getWord();
		String word_1 = context.getToken(-1).getWord();

		return new PREVBIGRAMRule(from, to, word_2, word_1);
	}
}