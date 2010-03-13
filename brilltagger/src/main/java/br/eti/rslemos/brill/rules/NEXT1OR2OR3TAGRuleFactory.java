package br.eti.rslemos.brill.rules;

import java.util.Arrays;
import java.util.Collection;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXT1OR2OR3TAGRuleFactory<T> extends AbstractRuleFactory<T> {
	@SuppressWarnings("unchecked")
	public Collection<Rule<T>> create(T from, T to, Context<T> context) {
		T tag1 = context.getToken(1).getTag();
		T tag2 = context.getToken(2).getTag();
		T tag3 = context.getToken(3).getTag();

		return Arrays.<Rule<T>> asList(
				new NEXT1OR2OR3TAGRule<T>(from, to, tag1), 
				new NEXT1OR2OR3TAGRule<T>(from, to, tag2),
				new NEXT1OR2OR3TAGRule<T>(from, to, tag3)
		);
	}
}