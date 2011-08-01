package br.eti.rslemos.brill.rules;

import java.util.Arrays;
import java.util.Collection;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXT1OR2OR3WDRuleFactory extends AbstractRuleFactory {
	
	public static final RuleFactory INSTANCE = new NEXT1OR2OR3WDRuleFactory();

	@Override
	public Collection<Rule> create(Object from, Object to, Context context) {
		String word1 = context.getToken(1).getWord();
		String word2 = context.getToken(2).getWord();
		String word3 = context.getToken(3).getWord();

		return Arrays.<Rule> asList(
				NEXT1OR2OR3WDRule.createRule(from, to, word1), 
				NEXT1OR2OR3WDRule.createRule(from, to, word2),
				NEXT1OR2OR3WDRule.createRule(from, to, word3)
		);
	}
}