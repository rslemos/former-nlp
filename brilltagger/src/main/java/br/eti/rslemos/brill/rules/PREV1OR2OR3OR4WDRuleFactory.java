package br.eti.rslemos.brill.rules;

import java.util.Arrays;
import java.util.Collection;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREV1OR2OR3OR4WDRuleFactory extends AbstractRuleFactory {
	
	public static final RuleFactory INSTANCE = new PREV1OR2OR3OR4WDRuleFactory();

	@Override
	public Collection<Rule> create(Object from, Object to, Context context) {
		String word_1 = context.getToken(-1).getWord();
		String word_2 = context.getToken(-2).getWord();
		String word_3 = context.getToken(-3).getWord();
		String word_4 = context.getToken(-4).getWord();

		return Arrays.<Rule> asList(
				PREV1OR2OR3OR4WDRule.createRule(from, to, word_1), 
				PREV1OR2OR3OR4WDRule.createRule(from, to, word_2),
				PREV1OR2OR3OR4WDRule.createRule(from, to, word_3),
				PREV1OR2OR3OR4WDRule.createRule(from, to, word_4)
		);
	}
}