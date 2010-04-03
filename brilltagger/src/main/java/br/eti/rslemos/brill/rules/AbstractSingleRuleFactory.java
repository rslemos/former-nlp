package br.eti.rslemos.brill.rules;

import java.util.Collection;
import java.util.Collections;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public abstract class AbstractSingleRuleFactory extends AbstractRuleFactory {

	@Override
	protected Collection<Rule> create(Object from, Object to, Context context) {
		return Collections.singleton(createRule(from, to, context));
	}

	protected abstract Rule createRule(Object from, Object to, Context context);

}
