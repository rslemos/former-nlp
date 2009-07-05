package br.eti.rslemos.ad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

public class ADCorpus implements Iterable<Extract> {

	private final BufferedReader input;
	private int lineNumber;
	String line;
	
	private Iterator<Extract> extracts;
	
	public ADCorpus(Reader input) {
		this.input = new BufferedReader(input);
		readNextLine();

		extracts = new ExtractIterator(this);
	}

	void readNextLine() {
		try {
			do {
				line = input.readLine();
				lineNumber++;
			} while (line != null && ( 
						line.equals("<s frag>")
						|| line.startsWith("### ")
						|| (line.startsWith("<lixo") && line.endsWith(">"))
					));
		} catch (IOException e) {
			line = null;
		}
		if (line == null)
			line = "";
	}
	
	void assertLineStartsWith(String expected) {
		assert line.startsWith(expected) : "At line " + lineNumber + ": expected '" + expected + "...'; found '" + line + "'";
	}

	void assertLineEquals(String expected) {
		assert line.equals(expected) : "At line " + lineNumber + ": expected '" + expected + "'; found '" + line + "'";
	}
	
	void assertLineContains(String expected) {
		assert line.contains(expected) : "At line " + lineNumber + ": expected '..." + expected + "...'; found '" + line + "'";
	}

	void assertBoolean(boolean b) {
		assert b : "At line " + lineNumber;
	}

	public Iterator<Extract> extracts() {
		return extracts;
	}

	public Iterator<Extract> iterator() {
		return extracts();
	}

	private static class ExtractIterator extends BaseIterator<Extract> {
		private ExtractIterator(ADCorpus corpus) {
			super(corpus);
		}
		
		@Override
		protected void tail() {
			try {
				corpus.input.close();
			} catch (IOException e) {
			}
		}

		@Override
		protected boolean testForNext() {
			return corpus.line.startsWith("<ext");
		}

		@Override
		protected Extract buildNext() {
			return new Extract(corpus);
		}
	}

}
