package br.eti.rslemos.ad;

import java.util.ArrayList;

public class Info_pron_indef extends Info {

	private final String lemma;
	private final String[] secondary;
	private final String unknown;
	private final String gender;
	private final String number;

	Info_pron_indef(String info_chunk) {
		// "o" <artd> DET F P
		String[] parts = info_chunk.split(" ");
		
		assert parts[0].startsWith("\"");
		assert parts[0].endsWith("\"");
	
		lemma = parts[0].substring("\"".length(), parts[0].length() - "\"".length());

		ArrayList<String> al = new ArrayList<String>(parts.length);
		int i = 1;
		
		while(parts[i].charAt(0) == '<')
			al.add(parts[i++]);
		
		secondary = al.toArray(new String[al.size()]);
		
		unknown = parts[i+0];
		gender = parts[i+1];
		number = parts[i+2];
	}

	public String getLemma() {
		return lemma;
	}

	public String[] getSecondary() {
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
