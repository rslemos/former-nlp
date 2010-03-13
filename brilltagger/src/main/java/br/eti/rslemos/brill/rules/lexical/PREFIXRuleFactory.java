package br.eti.rslemos.brill.rules.lexical;

import br.eti.rslemos.brill.Rule;

public class PREFIXRuleFactory<T> extends AbstractAFFIXRuleFactory<T> {
	@Override
	protected Rule<T> create(T from, T to, String word, int length) {
		return new PREFIXRule<T>(from, to, word.substring(0, length));
	}
}