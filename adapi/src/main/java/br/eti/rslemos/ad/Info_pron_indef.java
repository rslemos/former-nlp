package br.eti.rslemos.ad;

public class Info_pron_indef extends Info {

	private final String lemma;
	private final String secondary;
	private final String unknown;
	private final String gender;
	private final String number;

	Info_pron_indef(String info_chunk) {
		// "o" <artd> DET F P
		String[] parts = info_chunk.split(" ");
		
		assert parts[0].startsWith("\"");
		assert parts[0].endsWith("\"");
	
		lemma = parts[0].substring("\"".length(), parts[0].length() - "\"".length());
		
		if (parts[1].charAt(0) == '<') {
			secondary = parts[1];
			parts[1] = parts[2];
			parts[2] = parts[3];
			parts[3] = parts[4];
		} else {
			secondary = null;
		}

		unknown = parts[1];
		gender = parts[2];
		number = parts[3];
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

	public String getGender() {
		return gender;
	}

	public String getNumber() {
		return number;
	}

}
