package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class RBIGRAMRuleFactory<T> extends AbstractSingleRuleFactory<T> {
	public Rule<T> createRule(T from, T to, Context<T> context) {
		String word0 = context.getToken(0).getWord();
		String word1 = context.getToken(1).getWord();

		return new RBIGRAMRule<T>(from, to, word0, word1);
	}
}