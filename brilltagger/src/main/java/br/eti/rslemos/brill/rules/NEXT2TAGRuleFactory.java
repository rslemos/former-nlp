package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXT2TAGRuleFactory<T> extends AbstractSingleRuleFactory<T> {
	public Rule<T> createRule(T from, T to, Context<T> context) {
		T tag2 = context.getToken(2).getTag();

		return new NEXT2TAGRule<T>(from, to, tag2);
	}
}