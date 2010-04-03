package br.eti.rslemos.brill.rules.lexical;

import br.eti.rslemos.brill.Rule;

public class SUFFIXRuleFactory extends AbstractAFFIXRuleFactory {
	@Override
	protected Rule create(Object from, Object to, String word, int length) {
		return new SUFFIXRule(from, to, word.substring(word.length() - length, word.length()));
	}
}