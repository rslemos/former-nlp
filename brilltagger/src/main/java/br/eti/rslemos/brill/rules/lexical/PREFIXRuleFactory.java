package br.eti.rslemos.brill.rules.lexical;

import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Tag;

public class PREFIXRuleFactory extends AbstractAFFIXRuleFactory {
	@Override
	protected Rule create(Tag from, Tag to, String word, int length) {
		return new PREFIXRule(from, to, word.substring(0, length));
	}
}