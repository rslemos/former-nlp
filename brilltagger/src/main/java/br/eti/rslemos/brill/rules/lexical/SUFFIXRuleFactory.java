package br.eti.rslemos.brill.rules.lexical;

import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.rules.RuleFactory;

public class SUFFIXRuleFactory extends AbstractAFFIXRuleFactory {
	public static final RuleFactory INSTANCE = new SUFFIXRuleFactory();

	@Override
	protected Rule create(Object from, Object to, String word, int length) {
		return SUFFIXRule.createRule(from, to, word.substring(word.length() - length, word.length()));
	}
}