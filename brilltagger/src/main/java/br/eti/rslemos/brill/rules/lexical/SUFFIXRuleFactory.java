package br.eti.rslemos.brill.rules.lexical;

import br.eti.rslemos.brill.Rule;

public class SUFFIXRuleFactory<T> extends AbstractAFFIXRuleFactory<T> {
	@Override
	protected Rule<T> create(T from, T to, String word, int length) {
		return new SUFFIXRule<T>(from, to, word.substring(word.length() - length, word.length()));
	}
}