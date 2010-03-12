package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDNEXTTAGRuleFactory<T> extends AbstractSingleRuleFactory<T> {
	public Rule<T> createRule(T from, T to, Context<T> context) {
		String word0 = context.getToken(0).getWord();
		T tag1 = context.getToken(1).getTag();

		return new WDNEXTTAGRule<T>(from, to, word0, tag1);
	}
}