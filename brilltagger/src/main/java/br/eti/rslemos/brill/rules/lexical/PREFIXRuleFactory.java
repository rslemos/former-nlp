package br.eti.rslemos.brill.rules.lexical;

import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.rules.RuleFactory;

public class PREFIXRuleFactory extends AbstractAFFIXRuleFactory {
	public static final RuleFactory INSTANCE = new PREFIXRuleFactory();

	@Override
	protected Rule create(Object from, Object to, String word, int length) {
		return PREFIXRule.createRule(from, to, word.substring(0, length));
	}
}