package br.eti.rslemos.brill.rules;

import java.util.Arrays;
import java.util.Collection;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREV1OR2OR3OR4WDRuleFactory<T> extends AbstractRuleFactory<T> {
	public Collection<Rule<T>> create(T from, T to, Context<T> context) {
		String word_1 = context.getToken(-1).getWord();
		String word_2 = context.getToken(-2).getWord();
		String word_3 = context.getToken(-3).getWord();
		String word_4 = context.getToken(-4).getWord();

		return Arrays.<Rule<T>> asList(new PREV1OR2OR3OR4WDRule<T>(from, to,
				word_1), new PREV1OR2OR3OR4WDRule<T>(from, to, word_2),
				new PREV1OR2OR3OR4WDRule<T>(from, to, word_3),
				new PREV1OR2OR3OR4WDRule<T>(from, to, word_4));
	}
}