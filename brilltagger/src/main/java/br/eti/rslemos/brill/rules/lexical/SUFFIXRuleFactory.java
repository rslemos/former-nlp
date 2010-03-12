package br.eti.rslemos.brill.rules.lexical;

import java.util.ArrayList;
import java.util.Collection;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.rules.AbstractRuleFactory;

public class SUFFIXRuleFactory<T> extends AbstractRuleFactory<T> {
	@Override
	public Collection<Rule<T>> create(T from, T to, Context<T> context) {
		return create(from, to, context.getToken(0).getWord());
	}

	public Collection<Rule<T>> create(T from, T to, String word0) {
		Collection<Rule<T>> result = new ArrayList<Rule<T>>(
				word0.length() - 1);

		for (int i = 1; i < word0.length(); i++) {
			result.add(new SUFFIXRule<T>(from, to, word0.substring(word0.length() - i, word0.length())));
		}

		return result;
	}
}