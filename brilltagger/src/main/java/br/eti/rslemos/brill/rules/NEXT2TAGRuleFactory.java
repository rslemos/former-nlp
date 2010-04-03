package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Tag;

public class NEXT2TAGRuleFactory extends AbstractSingleRuleFactory {
	public Rule createRule(Tag from, Tag to, Context context) {
		Tag tag2 = context.getToken(2).getTag();

		return new NEXT2TAGRule(from, to, tag2);
	}
}