package br.eti.rslemos.brill;

import java.util.Collection;

interface RuleProducingStrategy {
	Collection<Rule> produceAllPossibleRules(Context context, Token target);
}