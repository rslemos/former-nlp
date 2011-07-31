package br.eti.rslemos.brill;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class HamcrestRuleAdapter extends AbstractHamcrestRule {
	private final Matcher<Context> adaptee;

	public HamcrestRuleAdapter(Matcher<Context> adaptee) {
		super();
		this.adaptee = adaptee;
	}

	public HamcrestRuleAdapter(Object from, Object to, Matcher<Context> adaptee) {
		super(from, to);
		this.adaptee = adaptee;
	}

	public void describeTo(Description description) {
		adaptee.describeTo(description);
	}

	@Override
	public boolean matches(Context context) {
		return super.matches(context) ? adaptee.matches(context) : false;
	}
	
	public static HamcrestRule ruleThat(Object from, Object to, Matcher<Context> matcher) {
		return new HamcrestRuleAdapter(from, to, matcher);
	}
}
