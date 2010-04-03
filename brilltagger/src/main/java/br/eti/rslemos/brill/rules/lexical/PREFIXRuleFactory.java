package br.eti.rslemos.brill.rules.lexical;

import br.eti.rslemos.brill.Rule;

public class PREFIXRuleFactory extends AbstractAFFIXRuleFactory {
	@Override
	protected Rule create(Object from, Object to, String word, int length) {
		return new PREFIXRule(from, to, word.substring(0, length));
	}
}