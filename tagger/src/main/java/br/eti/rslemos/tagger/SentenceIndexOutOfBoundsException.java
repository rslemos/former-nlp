package br.eti.rslemos.tagger;

public class SentenceIndexOutOfBoundsException extends IndexOutOfBoundsException {
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
