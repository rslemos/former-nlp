package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Token;

public interface RuleFactory<T> {

	Rule<T> create(Context<T> context, Token<T> target) throws RuleCreationException;

}