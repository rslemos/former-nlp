package br.eti.rslemos.brill.rules;

import java.util.Arrays;
import java.util.Collection;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREV1OR2WDRuleFactory<T> extends AbstractRuleFactory<T> {
	@SuppressWarnings("unchecked")
	public Collection<Rule<T>> create(T from, T to, Context<T> context) {
		String word_1 = context.getToken(-1).getWord();
		String word_2 = context.getToken(-2).getWord();

		return Arrays.<Rule<T>> asList(
				new PREV1OR2WDRule<T>(from, to, word_1),
				new PREV1OR2WDRule<T>(from, to, word_2)
		);
	}
}