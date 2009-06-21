package br.eti.rslemos.ad;

public class Info_n extends Info {

	private final String lemma;
	private final String secondary;
	private final String gender;
	private final String number;

	Info_n(String info_chunk) {
		// "consolação" <act> F S
		String[] parts = info_chunk.split(" ");
		
		assert parts[0].startsWith("\"");
		assert parts[0].endsWith("\"");
	
		lemma = parts[0].substring("\"".length(), parts[0].length() - "\"".length());
		
		if (parts[1].charAt(0) == '<') {
			secondary = parts[1];
			parts[1] = parts[2];
			parts[2] = parts[3];
		} else {
			secondary = null;
		}

		gender = parts[1];
		number = parts[2];
}

	public String getLemma() {
		return lemma;
	}

	public String getSecondary() {
		return secondary;
	}

	public String getGender() {
		return gender;
	}

	public String getNumber() {
		return number;
	}

}
