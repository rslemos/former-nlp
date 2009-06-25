package br.eti.rslemos.ad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

public class ADCorpus implements Iterable<Extract> {

	private final BufferedReader input;
	int lineNumber;
	String line;
	private Iterator<Extract> extracts;
	
	public ADCorpus(Reader input) {
		this.input = new BufferedReader(input);
		readNextLine();

		extracts = new ExtractIterator(this);
	}

	void readNextLine() {
		try {
			line = input.readLine();
			lineNumber++;
		} catch (IOException e) {
			line = null;
		}
		if (line == null)
			line = "";
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
