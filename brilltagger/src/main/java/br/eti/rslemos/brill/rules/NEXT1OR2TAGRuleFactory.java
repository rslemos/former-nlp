package br.eti.rslemos.brill.rules;

import java.util.Arrays;
import java.util.Collection;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXT1OR2TAGRuleFactory extends AbstractRuleFactory {
	
	public static final RuleFactory INSTANCE = new NEXT1OR2TAGRuleFactory();

	@Override
	public Collection<Rule> create(Object from, Object to, Context context) {
		Object tag1 = context.getToken(1).getTag();
		Object tag2 = context.getToken(2).getTag();

		return Arrays.<Rule> asList(
				new NEXT1OR2TAGRule(from, to, tag1),
				new NEXT1OR2TAGRule(from, to, tag2)
		);
	}
}