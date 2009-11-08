package br.eti.rslemos.brill;

public class SentenceIndexOutOfBounds extends IndexOutOfBoundsException {
    public SentenceIndexOutOfBounds() {
        super();
    }

    public SentenceIndexOutOfBounds(int index) {
        super("Sentence index out of range: " + index);
    }

    public SentenceIndexOutOfBounds(String s) {
        super(s);
    }
}
