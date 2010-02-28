package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Token;

public abstract class AbstractRuleFactory<T> implements RuleFactory<T> {
	public Rule<T> create(Context<T> context, Token<T> target) throws RuleCreationException {
		return create(context.getToken(0).getTag(), target.getTag(), context);
	}

	protected abstract Rule<T> create(T from, T to, Context<T> context) throws RuleCreationException;
}
