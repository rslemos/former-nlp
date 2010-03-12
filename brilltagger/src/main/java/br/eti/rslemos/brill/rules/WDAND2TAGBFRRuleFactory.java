package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDAND2TAGBFRRuleFactory<T> extends AbstractSingleRuleFactory<T> {
	public Rule<T> createRule(T from, T to, Context<T> context) {
		T tag_2 = context.getToken(-2).getTag();
		String word0 = context.getToken(0).getWord();

		return new WDAND2TAGBFRRule<T>(from, to, tag_2, word0);
	}
}