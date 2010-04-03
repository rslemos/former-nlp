package br.eti.rslemos.brill.rules;

import java.util.Arrays;
import java.util.Collection;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREV1OR2OR3WDRuleFactory extends AbstractRuleFactory {
	
	public Collection<Rule> create(Object from, Object to, Context context) {
		String word_1 = context.getToken(-1).getWord();
		String word_2 = context.getToken(-2).getWord();
		String word_3 = context.getToken(-3).getWord();

		return Arrays.<Rule> asList(
				new PREV1OR2OR3WDRule(from, to, word_1), 
				new PREV1OR2OR3WDRule(from, to, word_2),
				new PREV1OR2OR3WDRule(from, to, word_3)
		);
	}
}