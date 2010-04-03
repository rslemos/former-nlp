package br.eti.rslemos.brill.rules;

import java.util.Arrays;
import java.util.Collection;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Tag;

public class NEXT1OR2WDRuleFactory extends AbstractRuleFactory {
	
	public Collection<Rule> create(Tag from, Tag to, Context context) {
		String word1 = context.getToken(1).getWord();
		String word2 = context.getToken(2).getWord();

		return Arrays.<Rule> asList(
				new NEXT1OR2WDRule(from, to, word1),
				new NEXT1OR2WDRule(from, to, word2)
		);
	}
}