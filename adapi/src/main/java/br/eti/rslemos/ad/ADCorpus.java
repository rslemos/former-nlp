package br.eti.rslemos.ad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

public class ADCorpus {

	private final BufferedReader input;
	String line;
	
	public ADCorpus(InputStreamReader input) {
		this.input = new BufferedReader(input);
		readNextLine();
	}

	void readNextLine() {
		try {
			line = this.input.readLine();
		} catch (IOException e) {
		}
	}

	public Iterator<Extract> extracts() {
		return new Iterator<Extract>() {

			public boolean hasNext() {
				return line.startsWith("<ext");
			}

			public Extract next() {
				return new Extract(ADCorpus.this);
			}

			public void remove() {
			}
			
		};
	}

}
