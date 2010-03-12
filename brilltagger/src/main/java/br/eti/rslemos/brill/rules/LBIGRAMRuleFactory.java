package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class LBIGRAMRuleFactory<T> extends AbstractSingleRuleFactory<T> {
	public Rule<T> createRule(T from, T to, Context<T> context) {
		String word0 = context.getToken(0).getWord();
		String word_1 = context.getToken(-1).getWord();

		return new LBIGRAMRule<T>(from, to, word_1, word0);
	}
}