package br.eti.rslemos.brill.rules.lexical;

import java.util.ArrayList;
import java.util.Collection;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.rules.AbstractRuleFactory;

public abstract class AbstractAFFIXRuleFactory extends AbstractRuleFactory {

	public AbstractAFFIXRuleFactory() {
		super();
	}

	protected abstract Rule create(Object from, Object to, String word, int length);

	@Override
	public Collection<Rule> create(Object from, Object to, Context context) {
		return create(from, to, context.getToken(0).getWord());
	}

	private Collection<Rule> create(Object from, Object to, String word) {
		Collection<Rule> result = new ArrayList<Rule>(word.length() - 1);
	
		for (int i = 1; i < word.length(); i++) {
			result.add(create(from, to, word, i));
		}
	
		return result;
	}

}