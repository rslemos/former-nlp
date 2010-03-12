package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDAND2TAGAFTRuleFactory<T> extends AbstractSingleRuleFactory<T> {
	public Rule<T> createRule(T from, T to, Context<T> context) {
		String word0 = context.getToken(0).getWord();
		T tag2 = context.getToken(2).getTag();

		return new WDAND2TAGAFTRule<T>(from, to, word0, tag2);
	}
}