package br.eti.rslemos.brill;

public interface Rule {

	Object getFrom();

	Object getTo();
	
	
	boolean matches(Context context);
	
	boolean apply(Context context);

	boolean equals(Object o);

	int hashCode();

	boolean firingDependsOnObject(Object tag);

}