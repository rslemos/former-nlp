package br.eti.rslemos.tiger;

import java.net.URI;

public interface Head {
	URI getExternal();
	Meta getMeta();
	Annotation getAnnotation();
}