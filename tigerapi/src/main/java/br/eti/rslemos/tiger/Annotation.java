package br.eti.rslemos.tiger;

import java.util.List;

public interface Annotation {
	List<? extends Feature> getFeatures();
	EdgeLabel getEdgeLabel();
	EdgeLabel getSecEdgeLabel();
}