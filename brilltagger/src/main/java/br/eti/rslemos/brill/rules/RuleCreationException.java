package br.eti.rslemos.brill.rules;

public class RuleCreationException extends Exception {

	private static final long serialVersionUID = 1946172401139265592L;

	protected RuleCreationException() {
		super();
	}

	protected RuleCreationException(String message, Throwable cause) {
		super(message, cause);
	}

	protected RuleCreationException(String message) {
		super(message);
	}

	protected RuleCreationException(Throwable cause) {
		super(cause);
	}
}
