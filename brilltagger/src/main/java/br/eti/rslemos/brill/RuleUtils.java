package br.eti.rslemos.brill;

public final class RuleUtils {
	private RuleUtils() {}

	public static boolean applyRule(Context context, Rule rule) {
		if (rule.matches(context)) {
			context.getToken(0).setTag(rule.getTo());
			return true;
		} else
			return false;
	}
}
