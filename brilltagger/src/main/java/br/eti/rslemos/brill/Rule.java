package br.eti.rslemos.brill;

public interface Rule {

	public Object getTarget();

	public void setTarget(Object target);

	public boolean matches();

	public boolean equals(Object o);

	public int hashCode();

}