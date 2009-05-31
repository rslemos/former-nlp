package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.Token;

public abstract class AbstractRuleFactory implements RuleFactory {
	public Rule create(Context context, Token target) throws RuleCreationException {
		return create(context.getToken(0).getTag(), target.getTag(), context);
	}

	protected abstract Rule create(String from, String to, Context context) throws RuleCreationException;
}
