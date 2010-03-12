package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDAND2AFTRuleFactory<T> extends AbstractSingleRuleFactory<T> {
	public Rule<T> createRule(T from, T to, Context<T> context) {
		String word0 = context.getToken(0).getWord();
		String word2 = context.getToken(2).getWord();

		return new WDAND2AFTRule<T>(from, to, word0, word2);
	}
}