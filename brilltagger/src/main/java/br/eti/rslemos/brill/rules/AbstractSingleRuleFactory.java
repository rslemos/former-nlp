package br.eti.rslemos.brill.rules;

import java.util.Collection;
import java.util.Collections;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Tag;

public abstract class AbstractSingleRuleFactory extends AbstractRuleFactory {

	@Override
	protected Collection<Rule> create(Tag from, Tag to, Context context) {
		return Collections.singleton(createRule(from, to, context));
	}

	protected abstract Rule createRule(Tag from, Tag to, Context context);

}
