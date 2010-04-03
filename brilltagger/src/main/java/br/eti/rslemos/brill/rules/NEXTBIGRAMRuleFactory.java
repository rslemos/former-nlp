package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXTBIGRAMRuleFactory extends AbstractSingleRuleFactory {
	public Rule createRule(Object from, Object to, Context context) {
		String word1 = context.getToken(1).getWord();
		String word2 = context.getToken(2).getWord();

		return new NEXTBIGRAMRule(from, to, word1, word2);
	}
}