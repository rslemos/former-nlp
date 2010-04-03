package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Tag;

public class RBIGRAMRuleFactory extends AbstractSingleRuleFactory {
	public Rule createRule(Tag from, Tag to, Context context) {
		String word0 = context.getToken(0).getWord();
		String word1 = context.getToken(1).getWord();

		return new RBIGRAMRule(from, to, word0, word1);
	}
}