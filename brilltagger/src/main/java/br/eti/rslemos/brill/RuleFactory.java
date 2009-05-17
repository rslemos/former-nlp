package br.eti.rslemos.brill;

public interface RuleFactory<R extends Rule> {
	R create(Context context, Token target) throws RuleCreationException;
}
