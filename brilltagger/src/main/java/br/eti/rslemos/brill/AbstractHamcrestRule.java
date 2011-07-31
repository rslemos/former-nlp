package br.eti.rslemos.brill;

public abstract class AbstractHamcrestRule extends AbstractRule implements HamcrestRule {

	public AbstractHamcrestRule() {
		super();
	}

	public AbstractHamcrestRule(Object from, Object to) {
		super(from, to);
	}

	public final boolean matches(Object item) {
		if (item instanceof Context)
			return matches((Context)item);
		else
			return false;
	}

	public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {
	}

}
