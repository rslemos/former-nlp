package br.eti.rslemos.brill.rules;

import java.util.Arrays;
import java.util.Collection;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREV1OR2TAGRuleFactory<T> extends AbstractRuleFactory<T> {
	@SuppressWarnings("unchecked")
	public Collection<Rule<T>> create(T from, T to, Context<T> context) {
		T tag_1 = context.getToken(-1).getTag();
		T tag_2 = context.getToken(-2).getTag();

		return Arrays.<Rule<T>> asList(
				new PREV1OR2TAGRule<T>(from, to, tag_1),
				new PREV1OR2TAGRule<T>(from, to, tag_2)
		);
	}
}