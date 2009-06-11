package br.eti.rslemos.brill;

public interface Rule {

	String getFrom();

	String getTo();
	
	
	boolean matches(Context context);
	
	boolean apply(Context context);

	boolean equals(Object o);

	int hashCode();

	boolean firingDependsOnTag(String tag);

}