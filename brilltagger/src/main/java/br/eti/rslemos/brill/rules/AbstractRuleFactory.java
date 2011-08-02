package br.eti.rslemos.brill.rules;

import java.util.Collection;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.AbstractToken;
import br.eti.rslemos.tagger.Token;

public abstract class AbstractRuleFactory implements RuleFactory {
	public Collection<Rule> create(Context context, Token target) {
		return create(context.getToken(0).getFeature(AbstractToken.POS), target.getFeature(AbstractToken.POS), context);
	}

	protected abstract Collection<Rule> create(Object from, Object to, Context context);
}
