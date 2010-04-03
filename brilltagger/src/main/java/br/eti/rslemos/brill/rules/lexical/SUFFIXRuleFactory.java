package br.eti.rslemos.brill.rules.lexical;

import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Tag;

public class SUFFIXRuleFactory extends AbstractAFFIXRuleFactory {
	@Override
	protected Rule create(Tag from, Tag to, String word, int length) {
		return new SUFFIXRule(from, to, word.substring(word.length() - length, word.length()));
	}
}