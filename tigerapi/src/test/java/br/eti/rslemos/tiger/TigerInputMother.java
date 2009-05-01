package br.eti.rslemos.tiger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

public class TigerInputMother {

	public static Reader getInputStraightTiger()
	throws UnsupportedEncodingException {
		return getResourceAsReader("/inputStraightTiger.xml", "UTF-8");
	}

	public static String getInputStraightTigerId() {
		return "TESTCORPUS";
	}

	private static Reader getResourceAsReader(String name, String encoding)
	throws UnsupportedEncodingException {
		InputStream stream = TigerInputMother.class.getResourceAsStream(name);
		return new InputStreamReader(stream, encoding);
	}

}