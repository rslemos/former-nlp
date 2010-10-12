package br.eti.rslemos.brill.rules.lexical;

import br.eti.rslemos.brill.AbstractBrillRule;
import br.eti.rslemos.brill.Context;

public abstract class AbstractLexicalBrillRule extends AbstractBrillRule {

	protected AbstractLexicalBrillRule() {
	}

	protected AbstractLexicalBrillRule(Object from, Object to) {
		super(from, to);
	}

	@Override
	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		return thisMatches(context.getToken(0).getWord());
	}

	protected abstract boolean thisMatches(String word0);

}
