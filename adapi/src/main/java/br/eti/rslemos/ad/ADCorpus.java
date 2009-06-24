package br.eti.rslemos.ad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

public class ADCorpus implements Iterable<Extract> {

	private final BufferedReader input;
	int lineNumber;
	String line;
	
	public ADCorpus(Reader input) {
		this.input = new BufferedReader(input);
		readNextLine();
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
		return new Iterator<Extract>() {

			public boolean hasNext() {
				if (line.startsWith("<ext")) {
					return true;
				} else {
					try {
						input.close();
					} catch (IOException e) {
					}
					
					return false;
				}
			}

			public Extract next() {
				return new Extract(ADCorpus.this);
			}

			public void remove() {
			}
			
		};
	}

	public Iterator<Extract> iterator() {
		return extracts();
	}

}
