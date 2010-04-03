package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Tag;

public class WDPREVTAGRuleFactory extends AbstractSingleRuleFactory {
	public Rule createRule(Tag from, Tag to, Context context) {
		String word0 = context.getToken(0).getWord();
		Tag tag_1 = context.getToken(-1).getTag();

		return new WDPREVTAGRule(from, to, tag_1, word0);
	}
}