package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;

public class NEXT1OR2OR3TAGRule extends AbstractRule {

	private final String next1or2or3Tag;

	public NEXT1OR2OR3TAGRule(String from, String to, String next1or2or3Tagt) {
		super(from, to);
		
		this.next1or2or3Tag = next1or2or3Tagt;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String tag1 = context.getToken(1).getTag();
		String tag2 = context.getToken(2).getTag();
		String tag3 = context.getToken(3).getTag();
		
		return next1or2or3Tag != null 
		? (next1or2or3Tag.equals(tag1) | next1or2or3Tag.equals(tag2) | next1or2or3Tag.equals(tag3)) 
		: (tag1 == null | tag2 == null | tag3 == null);
	}
}
