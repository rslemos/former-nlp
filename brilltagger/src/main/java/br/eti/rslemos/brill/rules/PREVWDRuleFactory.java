package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREVWDRuleFactory<T> extends AbstractSingleRuleFactory<T> {
	public Rule<T> createRule(T from, T to, Context<T> context) {
		String word_1 = context.getToken(-1).getWord();

		return new PREVWDRule<T>(from, to, word_1);
	}
}