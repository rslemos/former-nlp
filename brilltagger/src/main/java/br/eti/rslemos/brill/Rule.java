package br.eti.rslemos.brill;

import br.eti.rslemos.tagger.Tag;

public interface Rule {

	Tag getFrom();

	Tag getTo();
	
	
	boolean matches(Context context);
	
	boolean apply(Context context);

	boolean equals(Object o);

	int hashCode();

	boolean firingDependsOnTag(Tag tag);

}