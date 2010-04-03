package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREVWDRuleFactory extends AbstractSingleRuleFactory {
	public Rule createRule(Object from, Object to, Context context) {
		String word_1 = context.getToken(-1).getWord();

		return new PREVWDRule(from, to, word_1);
	}
}