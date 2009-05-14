package br.eti.rslemos.brill;

public interface Rule {

	public String getFrom();

	public String getTo();
	
	
	public boolean matches(Context context);
	
	public boolean apply(Context context);

	public boolean equals(Object o);

	public int hashCode();

}