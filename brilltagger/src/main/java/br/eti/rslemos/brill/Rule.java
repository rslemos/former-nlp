package br.eti.rslemos.brill;

import java.io.IOException;
import java.io.Writer;

public interface Rule {

	Object getFrom();

	Object getTo();
	
	boolean matches(Context context);
	
	boolean equals(Object o);

	int hashCode();

	boolean testsTag(Object tag);

	void writeRule(Writer out) throws IOException;
}