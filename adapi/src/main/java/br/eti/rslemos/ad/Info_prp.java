package br.eti.rslemos.ad;

public class Info_prp extends Info {

	private final String lemma;
	private final String secondary;
	private final String unknown;

	Info_prp(String info_chunk) {
		// "de" <sam-> <np-close>
		String[] parts = info_chunk.split(" ");
		
		assert parts[0].startsWith("\"");
		assert parts[0].endsWith("\"");
	
		lemma = parts[0].substring("\"".length(), parts[0].length() - "\"".length());
		
		if (parts[1].charAt(0) == '<') {
			secondary = parts[1];
			parts[1] = parts[2];
		} else {
			secondary = null;
		}

		if (parts[1].charAt(0) == '<') {
			unknown = parts[1];
			parts[1] = parts[2];
		} else {
			unknown = null;
		}
	}

	public String getLemma() {
		return lemma;
	}

	public String getSecondary() {
		return secondary;
	}

	public String getUnknown() {
		return unknown;
	}

}
