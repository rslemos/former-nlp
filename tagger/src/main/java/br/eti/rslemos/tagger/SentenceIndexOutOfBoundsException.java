package br.eti.rslemos.tagger;

public class SentenceIndexOutOfBoundsException extends IndexOutOfBoundsException {
	private static final long serialVersionUID = 3103173677203301062L;

	public SentenceIndexOutOfBoundsException() {
        super();
    }

    public SentenceIndexOutOfBoundsException(int index) {
        super("Sentence index out of range: " + index);
    }

    public SentenceIndexOutOfBoundsException(String s) {
        super(s);
    }
}
