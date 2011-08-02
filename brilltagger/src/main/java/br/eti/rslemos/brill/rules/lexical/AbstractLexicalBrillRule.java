package br.eti.rslemos.brill.rules.lexical;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.tagger.Token;

public abstract class AbstractLexicalBrillRule extends AbstractRule {

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
		return thisMatches((String)context.getToken(0).getFeature(Token.WORD));
	}

	protected abstract boolean thisMatches(String word0);

	@Override
	public String toBrillString() {
		return getTo() + " " + getType();
	}
}
