package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class SURROUNDTAGRuleFactory<T> extends AbstractSingleRuleFactory<T> {
	public Rule<T> createRule(T from, T to, Context<T> context) {
		T tag_1 = context.getToken(-1).getTag();
		T tag1 = context.getToken(1).getTag();

		return new SURROUNDTAGRule<T>(from, to, tag_1, tag1);
	}
}