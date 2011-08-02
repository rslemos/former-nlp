package br.eti.rslemos.brill;

import br.eti.rslemos.tagger.AbstractToken;

public final class RuleUtils {
	private RuleUtils() {}

	public static boolean applyRule(Context context, Rule rule) {
		if (rule.matches(context)) {
			context.getToken(0).setFeature(AbstractToken.POS, rule.getTo());
			return true;
		} else
			return false;
	}
}
