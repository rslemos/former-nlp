package br.eti.rslemos.brill.rules;

import java.util.Arrays;
import java.util.Collection;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREV1OR2WDRuleFactory extends AbstractRuleFactory {
	
	public Collection<Rule> create(Object from, Object to, Context context) {
		String word_1 = context.getToken(-1).getWord();
		String word_2 = context.getToken(-2).getWord();

		return Arrays.<Rule> asList(
				new PREV1OR2WDRule(from, to, word_1),
				new PREV1OR2WDRule(from, to, word_2)
		);
	}
}