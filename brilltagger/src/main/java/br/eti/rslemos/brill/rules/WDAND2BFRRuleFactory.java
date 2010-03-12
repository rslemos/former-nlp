package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDAND2BFRRuleFactory<T> extends AbstractSingleRuleFactory<T> {
	public Rule<T> createRule(T from, T to, Context<T> context) {
		String word_2 = context.getToken(-2).getWord();
		String word0 = context.getToken(0).getWord();

		return new WDAND2BFRRule<T>(from, to, word_2, word0);
	}
}