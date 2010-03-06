package br.eti.rslemos.brill.rules;

import java.util.Collection;
import java.util.Collections;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public abstract class AbstractSingleRuleFactory<T> extends AbstractRuleFactory<T> {

	@Override
	protected Collection<Rule<T>> create(T from, T to, Context<T> context) {
		return Collections.singleton(createRule(from, to, context));
	}

	protected abstract Rule<T> createRule(T from, T to, Context<T> context);

}
