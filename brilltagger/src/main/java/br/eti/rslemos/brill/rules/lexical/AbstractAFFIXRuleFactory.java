package br.eti.rslemos.brill.rules.lexical;

import java.util.ArrayList;
import java.util.Collection;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.rules.AbstractRuleFactory;

public abstract class AbstractAFFIXRuleFactory<T> extends AbstractRuleFactory<T> {

	public AbstractAFFIXRuleFactory() {
		super();
	}

	protected abstract Rule<T> create(T from, T to, String word, int length);

	@Override
	public Collection<Rule<T>> create(T from, T to, Context<T> context) {
		return create(from, to, context.getToken(0).getWord());
	}

	private Collection<Rule<T>> create(T from, T to, String word) {
		Collection<Rule<T>> result = new ArrayList<Rule<T>>(word.length() - 1);
	
		for (int i = 1; i < word.length(); i++) {
			result.add(create(from, to, word, i));
		}
	
		return result;
	}

}