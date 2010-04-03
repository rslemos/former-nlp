package br.eti.rslemos.brill.rules;

import java.util.Arrays;
import java.util.Collection;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXT1OR2OR3TAGRuleFactory extends AbstractRuleFactory {
	
	public Collection<Rule> create(Object from, Object to, Context context) {
		Object tag1 = context.getToken(1).getTag();
		Object tag2 = context.getToken(2).getTag();
		Object tag3 = context.getToken(3).getTag();

		return Arrays.<Rule> asList(
				new NEXT1OR2OR3TAGRule(from, to, tag1), 
				new NEXT1OR2OR3TAGRule(from, to, tag2),
				new NEXT1OR2OR3TAGRule(from, to, tag3)
		);
	}
}