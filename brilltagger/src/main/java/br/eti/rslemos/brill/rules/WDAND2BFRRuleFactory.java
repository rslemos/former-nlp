package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDAND2BFRRuleFactory extends AbstractSingleRuleFactory {
	public static final RuleFactory INSTANCE = new WDAND2BFRRuleFactory();

	@Override
	public Rule createRule(Object from, Object to, Context context) {
		String word_2 = context.getToken(-2).getWord();
		String word0 = context.getToken(0).getWord();

		return new WDAND2BFRRule(from, to, word_2, word0);
	}
}