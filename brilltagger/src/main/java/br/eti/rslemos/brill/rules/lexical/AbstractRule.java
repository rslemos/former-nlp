package br.eti.rslemos.brill.rules.lexical;

import br.eti.rslemos.brill.Context;

public abstract class AbstractRule<T> extends br.eti.rslemos.brill.AbstractRule<T> {

	protected AbstractRule() {
	}

	protected AbstractRule(T from, T to) {
		super(from, to);
	}

	@Override
	public boolean matches(Context<T> context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context<T> context) {
		return thisMatches(context.getToken(0).getWord());
	}

	protected abstract boolean thisMatches(String word0);

	@Override
	protected String toBrillText() {
		return getTo() + " " + getType();
	}
	
	
}
