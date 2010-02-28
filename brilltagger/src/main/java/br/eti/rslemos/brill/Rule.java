package br.eti.rslemos.brill;

public interface Rule<T> {

	T getFrom();

	T getTo();
	
	
	boolean matches(Context<T> context);
	
	boolean apply(Context<T> context);

	boolean equals(Object o);

	int hashCode();

	boolean firingDependsOnTag(T tag);

}